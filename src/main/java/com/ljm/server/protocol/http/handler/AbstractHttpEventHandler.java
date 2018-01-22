package com.ljm.server.protocol.http.handler;

import com.ljm.server.event.handler.AbstractEventHandler;
import com.ljm.server.event.handler.HandlerException;
import com.ljm.server.io.connection.Connection;
import com.ljm.server.protocol.http.HttpRequestMessage;
import com.ljm.server.protocol.http.HttpResponseMessage;

import java.io.IOException;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-01-2018/1/22
 */
public abstract class AbstractHttpEventHandler extends AbstractEventHandler<Connection> {
    @Override
    protected void doHandle(Connection connection) {
        HttpRequestMessage requestMessage = doParserRequestMessage(connection);
        HttpResponseMessage responseMessage = doGenerateResponseMessage(requestMessage);
        try {
            doTransferToClient(responseMessage, connection);
        } catch (IOException e) {
            throw new HandlerException(e);
        }
    }

    /**
     * 通过输入构造HttpRequestMessage
     *
     * @param connection
     * @return
     */
    protected abstract HttpRequestMessage doParserRequestMessage(Connection connection);

    /**
     * 根据HttpRequestMessage生成HttpResponseMessage
     *
     * @param httpRequestMessage
     * @return
     */
    protected abstract HttpResponseMessage doGenerateResponseMessage(
            HttpRequestMessage httpRequestMessage);

    /**
     * 写入HttpResponseMessage到客户端
     *
     * @param responseMessage
     */
    protected abstract void doTransferToClient(HttpResponseMessage responseMessage,
                                               Connection connection) throws IOException;
}
