package org.example;

/**
 * @author zhangsheng
 * Time 2024/1/10 9:02
 * 一个简单的HTTP服务器 获取本地JSON数据
 */
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static void main(String[] args) throws Exception {
        // 创建一个HTTP服务器，监听端口8070
        HttpServer server = HttpServer.create(new InetSocketAddress(8070), 0);

        // 创建一个上下文路径为 "/getData" 的处理器
        server.createContext("/getData", new GetDataHandler());

        // 启动服务器
        server.start();
        System.out.println("Server is running on port " + 8070);
    }

    static class GetDataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // 读取本地的JSON数据（假设数据存储在一个文件中）
            File jsonFile = new File("D:\\Deskstop\\工作\\浦东\\东方网对接\\weather.json");
            FileInputStream fis = new FileInputStream(jsonFile);
            byte[] data = new byte[(int) jsonFile.length()];
            fis.read(data);
            fis.close();

            // 设置响应头，指定响应的内容类型为JSON
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            // 发送响应
            exchange.sendResponseHeaders(200, data.length);
            OutputStream os = exchange.getResponseBody();
            os.write(data);
            os.close();
        }
    }

}
