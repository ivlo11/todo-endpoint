package com.ivonneroberts.todo.generator;

import com.sun.codemodel.ClassType
import com.sun.codemodel.JCodeModel
import static com.sun.codemodel.JMod.*
import static com.sun.codemodel.JExpr.lit;

public class ConstantsGenerator {

	public static void generateConstantsClass(final File outputDir) {
		def WEB_CLIENT_ID = '1-android-apps.googleusercontent.com';
		def ANDROID_CLIENT_ID = '2-android-apps.googleusercontent.com';
		def IOS_CLIENT_ID = '3-android-apps.googleusercontent.com';
		def ANDROID_AUDIENCE = WEB_CLIENT_ID;
		def EMAIL_SCOPE = 'https://www.googleapis.com/auth/userinfo.email';

		def configFile = ConstantsGenerator.class.getResource('constant.properties');
		if (configFile != null) {
			def constantConfig = new ConfigSlurper().parse(configFile);
			if (constantConfig.containsKey('WEB_CLIENT_ID')) {
				WEB_CLIENT_ID = constantConfig.getProperty('WEB_CLIENT_ID');
			}
			if (constantConfig.containsKey('ANDROID_CLIENT_ID')) {
				ANDROID_CLIENT_ID = constantConfig.getProperty('ANDROID_CLIENT_ID');
			}
			if (constantConfig.containsKey('IOS_CLIENT_ID')) {
				IOS_CLIENT_ID = constantConfig.getProperty('IOS_CLIENT_ID');
			}
			if (constantConfig.containsKey('ANDROID_AUDIENCE')) {
				ANDROID_AUDIENCE = constantConfig.getProperty('ANDROID_AUDIENCE');
			}
			if (constantConfig.containsKey('EMAIL_SCOPE')) {
				EMAIL_SCOPE = constantConfig.getProperty('EMAIL_SCOPE');
			}
		}

		def codeModel = new JCodeModel();
		def constantClass = codeModel._class(PUBLIC | FINAL, 'com.ivonneroberts.todo.Constants', ClassType.CLASS);
		def stringType = codeModel.ref(String.class);
		def field1 = constantClass.field(PUBLIC | STATIC | FINAL, stringType, 'WEB_CLIENT_ID', lit(WEB_CLIENT_ID));
		def field2 = constantClass.field(PUBLIC | STATIC | FINAL, stringType, 'ANDROID_CLIENT_ID', lit(ANDROID_CLIENT_ID));
		def field3 = constantClass.field(PUBLIC | STATIC | FINAL, stringType, 'IOS_CLIENT_ID', lit(IOS_CLIENT_ID));
		def field4 = constantClass.field(PUBLIC | STATIC | FINAL, stringType, 'ANDROID_AUDIENCE', lit(ANDROID_AUDIENCE));
		def field5 = constantClass.field(PUBLIC | STATIC | FINAL, stringType, 'EMAIL_SCOPE', lit(EMAIL_SCOPE));

		if (!outputDir.isDirectory() && !outputDir.mkdirs()) {
			throw new IOException('Could not create directory: ' + outputDir);
		}
		codeModel.build(outputDir);
	}
}
