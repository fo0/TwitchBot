package com.fo0.twitchbot.update;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.fo0.twitchbot.Main;
import com.fo0.twitchbot.utils.CONSTANTS;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.vdurmont.semver4j.Semver;

public class UpdateUtils {

	public static GitHubReleaseInfo getInfo() {
		GitHubReleaseInfo info = GitHubReleaseInfo.builder().build();
		try {
			GitHub gitHub = GitHub.connectAnonymously();
			GHRepository repository = gitHub.getRepository(CONSTANTS.GITHUB_URI);
			GHRelease latest = repository.getLatestRelease();
			String version = latest.getTagName().replaceAll("v", "");
			info.setVersion(version);
			info.setMessage(latest.getBody());
			boolean newerVersionAvailable = new Semver(latest.getTagName().replaceAll("v", ""))
					.isGreaterThan(new Semver(CONSTANTS.VERSION));
			if (newerVersionAvailable) {
				info.setAvailable(true);
			}

			return info;
		} catch (Exception e) {
			Logger.error("could not fetch any informations for repository: " + CONSTANTS.GITHUB_URI);
		}
		return info;
	}

	public static boolean isAvailable() {
		return getInfo().isAvailable();
	}

	public static void doUpdate() {
		try {
			GitHub gitHub = GitHub.connectAnonymously();
			GHRepository repository = gitHub.getRepository(CONSTANTS.GITHUB_URI);
			GHRelease latest = repository.getLatestRelease();
			boolean newerVersionAvailable = new Semver(latest.getTagName().replaceAll("v", ""))
					.isGreaterThan(new Semver(CONSTANTS.VERSION));
			if (!newerVersionAvailable) {
				Logger.info("no newer version available, skipping now");
				Logger.info("current version: " + CONSTANTS.VERSION);
				Logger.info("latest version: " + latest.getTagName().replaceAll("v", ""));
				return;
			} else {
				Logger.info("detected new version");
				Logger.info("current version: " + CONSTANTS.VERSION);
				Logger.info("latest version: " + latest.getTagName().replaceAll("v", ""));
			}

			GHAsset asset = latest.getAssets().get(0);
			Logger.info("downloading file from github: " + asset.getBrowserDownloadUrl());
			File latestFile = File.createTempFile(RandomStringUtils.randomAlphabetic(10), ".patch");
			latestFile.deleteOnExit();
			FileUtils.copyToFile(new URL(asset.getBrowserDownloadUrl()).openStream(), latestFile);
			Logger.info("download finished");
			Logger.info("applying patch...");
			File currentPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			Utils.writeBytesToFile(IOUtils.toByteArray(new FileInputStream(latestFile)), currentPath);
			// FileUtils.moveFile(latestFile, currentPath);
			try {
				latestFile.delete();
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}