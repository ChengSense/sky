package org.osgi.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.stereotype.Service;

public class HttpServer {

	private final AsynchronousChannelGroup channelGroup;
	private final AsynchronousServerSocketChannel server;
	private final static ConcurrentLinkedQueue<AsynchronousSocketChannel> queue = new ConcurrentLinkedQueue<AsynchronousSocketChannel>();
	private final static int processors = Runtime.getRuntime().availableProcessors();
	private final static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	public static void main(String[] args) throws IOException {
		HttpServer server = new HttpServer();
		server.listener();
	}

	public HttpServer() throws IOException {

		Integer processors = Runtime.getRuntime().availableProcessors();
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		channelGroup = AsynchronousChannelGroup.withFixedThreadPool(processors, threadFactory);// 设置线程数为CPU核数

		server = AsynchronousServerSocketChannel.open(channelGroup);
		server.setOption(StandardSocketOptions.SO_REUSEADDR, true);// 重用端口
		server.bind(new InetSocketAddress(8888), 800); // 绑定端口并设置连接请求队列长度
	}

	public void listener() {
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
			public void completed(AsynchronousSocketChannel socketChannel, Object obj) {
				server.accept(null, this);
				queue.add(socketChannel);
				readBuffer(socketChannel);
			}

			public void failed(Throwable exc, Object obj) {

			}
		});
	}

	private Charset charset = Charset.forName("UTF-8");

	private void readBuffer(final AsynchronousSocketChannel socketChannel) {
		final ByteBuffer readBuffer = ByteBuffer.allocate(1024);
		readBuffer.clear();
		socketChannel.read(readBuffer, null, new CompletionHandler<Integer, ByteBuffer>() {
			public void completed(Integer count, ByteBuffer buffer) {
				readBuffer.flip();
				if (count > 0)
					System.out.println(new String(readBuffer.array(), 0, count, charset));
				writeBuffer(socketChannel, readBuffer);
			}

			public void failed(Throwable exc, ByteBuffer attachment) {

			}
		});
	}

	private void writeBuffer(final AsynchronousSocketChannel socketChannel, final ByteBuffer buffer) {
		socketChannel.write(buffer, null, new CompletionHandler<Integer, ByteBuffer>() {
			public void completed(Integer result, ByteBuffer buffer) {
				try {
					socketChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public void failed(Throwable exc, ByteBuffer attachment) {

			}
		});
	}
}