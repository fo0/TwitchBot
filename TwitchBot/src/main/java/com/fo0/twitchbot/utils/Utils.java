package com.fo0.twitchbot.utils;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import com.google.common.collect.Lists;

public class Utils {

	public static boolean createNewFileWithDirectories(Path path) {
		if (!path.toFile().exists()) {
			try {
				if (!path.getParent().toFile().exists())
					Files.createDirectory(path.getParent());

				path.toFile().createNewFile();
				return true;
			} catch (IOException e) {
				Logger.error("failed to create file at path: " + path + " - " + e);
			}
		}
		
		return false;
	}
	
	public static boolean createNewFileWithDirectories(String pathStr) {
		return createNewFileWithDirectories(Paths.get(pathStr));
	}

	public static void sleep(long sleep) {
		sleep(TimeUnit.MILLISECONDS, sleep);
	}

	public static void sleep(TimeUnit unit, long sleep) {
		Logger.trace("Sleeping " + DurationFormatUtils.formatDuration(unit.toMillis(sleep), "HH:mm:ssS"));
		try {
			unit.sleep(sleep);
		} catch (InterruptedException e) {
		}
	}

	public static String getCurrentTime() {
		return DateFormatUtils.format(new Date(), "HH:mm:ss");
	}

	public static List<String> mapByRegEx(String str, String regEx) {
		Pattern pattern = Pattern.compile("\\$\\w+");
		Matcher m = pattern.matcher(str);
		List<String> list = Lists.newArrayList();
		while (m.find()) {
			// possible variable
			list.add(m.group());
		}
		return list;
	}

	public static boolean writeBytesToFile(byte[] bytes, File file) {
		try {
			FileUtils.writeByteArrayToFile(file, bytes);
		} catch (Exception e) {
			Logger.info("failed to override bytes from file" + e);
			return false;
		}
		return true;
	}

	public static void restartApplication(Class<?> CLAZZ, String[] MAIN_CLASS_ARGS) {
		Logger.info("Performing a restart");
		StringBuilder cmd = new StringBuilder();

		cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
		for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments())
			cmd.append(jvmArg + " ");
		cmd.append(" -cp ").append(CLAZZ.getName()).append(" ");
		cmd.append(" -jar ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
		for (String arg : MAIN_CLASS_ARGS)
			cmd.append(arg).append(" ");

		Logger.info("#####################################");
		Logger.info("#          Restarting now           #");
		Logger.info("# Restarting CMD: " + cmd.toString() + " #");
		Logger.info("#####################################");
		try {
			Runtime.getRuntime().exec(cmd.toString());
			System.exit(0);
		} catch (IOException e) {
			Logger.error("failed to restart application " + e);
		}

	}

}
