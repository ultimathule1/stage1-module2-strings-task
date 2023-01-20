package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        StringTokenizer strTok = new StringTokenizer(signatureString, "()");
        StringTokenizer contractWithoutParamTok = new StringTokenizer(strTok.nextToken(), " ");
        StringTokenizer argumentsTok = null;
        if (strTok.hasMoreTokens()) {
            argumentsTok = new StringTokenizer(strTok.nextToken(), ",");
        }

        String accessModifier,  returnType, methodName;
        List<String> arguments = new ArrayList<>();

        if (contractWithoutParamTok.countTokens() == 3) {
            accessModifier = contractWithoutParamTok.nextToken();
        } else {
            accessModifier = null;
        }
        returnType = contractWithoutParamTok.nextToken();
        methodName = contractWithoutParamTok.nextToken();

        if (argumentsTok != null) {
            while(argumentsTok.hasMoreTokens()) {
                arguments.add(argumentsTok.nextToken().trim());
            }
        }

        List<MethodSignature.Argument> argList = new ArrayList<>();

        //for(int i = 0; i < arguments.size(); i++) {
        for(String argument : arguments) {
            StringTokenizer stTemp = new StringTokenizer(argument, " ");
            argList.add(new MethodSignature.Argument(stTemp.nextToken(), stTemp.nextToken()));
        }

        MethodSignature methodSignature = new MethodSignature(methodName, argList);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);

        return methodSignature;
    }
}
