package com.nothing.realtime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class MyMovieStreamingTask implements Runnable {

	MyOwnSSeEmitter movieEmitter;

	public MyMovieStreamingTask(MyOwnSSeEmitter myOwnSSeEmitter) {
		this.movieEmitter = myOwnSSeEmitter;
	}

	public SseEmitter getMovieEmitter() {
		return movieEmitter;
	}

	public void setMovieEmitter(MyOwnSSeEmitter movieEmitter) {
		this.movieEmitter = movieEmitter;
	}

	@Override
	public void run() {

		System.out.println("movie streaming task...");

		// read the movie from the file system.
		File movie = new File("howlongdemo.mp4");
		File dst = new File("streamed.mp4");

		try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(movie));
				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dst));) {

			// buffered reading of movie
			byte[] buffer = new byte[1024];

			int frames; // 0
			while ((frames = inputStream.read(buffer)) > 0) {

				// transmit the movie to the client.
			    outputStream.write(buffer, 0, frames);
				outputStream.flush();


			}


		} catch (Exception e) {

			System.out.println("exception "+ e);
		}

	}

}
