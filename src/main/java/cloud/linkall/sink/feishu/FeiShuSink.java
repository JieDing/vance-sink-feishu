package cloud.linkall.sink.feishu;

import cloud.linkall.common.json.JsonMapper;
import cloud.linkall.core.http.WebhookServer;
import io.cloudevents.core.message.MessageReader;
import io.cloudevents.http.vertx.VertxMessageFactory;
import io.vertx.core.json.JsonObject;

public class FeiShuSink {
    public static void main(String[] args) {
        WebhookServer webhookServer = new WebhookServer();
        JsonObject userConfig = webhookServer.getUserConfig();

        webhookServer.requestHandler((req)->{
            VertxMessageFactory.createReader(req)
                    .map(MessageReader::toEvent)
                    .onSuccess(event -> {
                        JsonObject js = JsonMapper.wrapCloudEvent(event);
                        System.out.println("received a CloudEvent");
                        //System.out.println(js.encodePrettily());
                        webhookServer.getSinks().forEach((url)->{
                            webhookServer.sendJsonObject(url.toString(),js);
                        });
                                   

                    })
                    .onFailure(System.err::println);
        });

      
        webhookServer.listen(Integer.parseInt(userConfig.getString("port")));

    }


}
