package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        List<MethodSignature.Argument> argumentList = new ArrayList<>();
        String accessModifier = "";
        String returnType = "";
        String methodName;

        String[] split0 = signatureString.split("\\(");
        if (split0[1].length() > 1) {
            argumentList = getArguments(split0[1]);
        }

        String[] split1 = split0[0].split(" ");
        switch (split1.length) {
            default:
                throw new UnsupportedOperationException("Unknown signature.");
            case 3:
                accessModifier = split1[split1.length - 3];
            case 2:
                returnType = split1[split1.length - 2];
            case 1:
                methodName = split1[split1.length - 1];
        }

        MethodSignature methodSignature;

        if (argumentList.isEmpty()) {
            methodSignature = new MethodSignature(methodName);
        } else {
            methodSignature = new MethodSignature(methodName, argumentList);
        }
        methodSignature.setAccessModifier(accessModifier.isBlank() ? null : accessModifier);
        methodSignature.setReturnType(returnType.isBlank() ? null : returnType);

        return methodSignature;
    }

    private List<MethodSignature.Argument> getArguments(String s) {
        String tmp = s.substring(0, s.length() - 1);
        String[] argsArray = tmp.split(",");

        return Arrays.stream(argsArray)
                .map(e -> e.trim().split(" "))
                .map(e -> new MethodSignature.Argument(e[0], e[1]))
                .collect(Collectors.toList());
    }
}
