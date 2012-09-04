package com.neoriddle.mailutils;

public class TestGmailUtils {
    public static void main(String[] args) throws Exception {
	String username = "neoriddle@gmail.com";
	String password = "BaLaM02ii85";

	String[] tos = {"neoriddle@gmail.com"};
	String message = "This is a test";
	String subject = "TestGmailUtils foo mail";

	GmailUtils gu = new GmailUtils(username, password);
	gu.send(tos, null, null, message, subject);
    }
}