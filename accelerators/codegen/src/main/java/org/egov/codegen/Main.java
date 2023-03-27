package org.egov.codegen;

import io.swagger.codegen.v3.ClientOptInput;
import io.swagger.codegen.v3.ClientOpts;
import io.swagger.codegen.v3.CodegenConfig;
import io.swagger.codegen.v3.DefaultGenerator;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.apache.commons.cli.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        String url;
        String basePackage;
        String artifactId ;
        String groupId = "org.egov";
        boolean useLombok = false;
        boolean useTracer = false;

        Options options = new Options();
        options.addOption("u", "url", true, "URL of the Swagger YAML");
        options.addOption("b", "basePackage", true,"Base Package");
        options.addOption("a", "artifactId",true,"Artifact ID / context path of the artifact");
        options.addOption("g", "groupId",true,"Group ID of the artifact, default org.egov");
        options.addOption("l", "Use Lombok");
        options.addOption("t", "Use Tracer");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);

            if(cmd.hasOption("u")) {
                url = cmd.getOptionValue('u');
                if(url == null){
                    usage(options);
                    return;
                }

            }
            else {
                usage(options);
                return;
            }

            if(cmd.hasOption("b")) {
                basePackage = cmd.getOptionValue('b');
                if(basePackage == null){
                    usage(options);
                    return;
                }
            }
            else {
                usage(options);
                return;
            }

            if(cmd.hasOption("a")) {
                artifactId = cmd.getOptionValue('a');
                if(artifactId == null){
                    usage(options);
                    return;
                }
            }
            else {
                usage(options);
                return;
            }

            if(cmd.hasOption("g"))
                groupId = cmd.getOptionValue('g');

            if(cmd.hasOption("l"))
                useLombok = true;

            if(cmd.hasOption("t"))
                useTracer = true;


            File outputDir = new File("."+ File
                    .separator + "output");

            if(!outputDir.exists()) {
                if (!outputDir.mkdirs()) {
                    System.out.println("Unable to create directory. " + outputDir.getAbsolutePath());
                    return;
                }
            }

            Config config = new Config(url,groupId, artifactId, basePackage, useLombok, useTracer);
            generateCode(config, outputDir.getAbsolutePath());


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static void generateCode(Config config, String outputDir){
//        final Swagger swagger = new SwaggerParser().read(config.getUrl());
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);
        parseOptions.setResolveRequestBody(true);
        final OpenAPI openAPI = new OpenAPIParser().readLocation(config.getUrl(), null,parseOptions).getOpenAPI();
        CodegenConfig codegenConfig = new SpringBootCodegen(config, outputDir);


        ClientOptInput clientOptInput = new ClientOptInput().opts(new ClientOpts()).openAPI(openAPI).config(codegenConfig);

        DefaultGenerator gen = new DefaultGenerator();
        gen.setGenerateSwaggerMetadata(false);
        gen.opts(clientOptInput);
        gen.generate();
    }

    private static void usage(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Codegen", options);
    }


}
