package com.epam.mjc;

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

        MethodSignature methodSignature = new MethodSignature("");

        // Extract access modifier if present
        String[] parts = signatureString.split("\\s+", 2);
        String firstPart = parts[0];
        String remainder = parts[1];

        if (firstPart.equals("public") || firstPart.equals("private") || firstPart.equals("protected")) {
            methodSignature.setAccessModifier(firstPart);
            parts = remainder.split("\\s+", 2);
        } else {
            parts = signatureString.split("\\s+", 2);
        }

        // Extract return type
        methodSignature.setReturnType(parts[0]);
        remainder = parts[1];

        // Extract method name and arguments
        String methodName = remainder.substring(0, remainder.indexOf('('));
        methodSignature.setMethodName(methodName);

        String argumentsString = remainder.substring(remainder.indexOf('(') + 1, remainder.lastIndexOf(')'));

        if (!argumentsString.isEmpty()) {
            String[] argumentsArray = argumentsString.split(", ");
            for (String argument : argumentsArray) {
                String[] argumentParts = argument.split(" ");
                methodSignature.getArguments().add(new MethodSignature.Argument(argumentParts[0], argumentParts[1]));
            }
        }

        return methodSignature;
    }
}

