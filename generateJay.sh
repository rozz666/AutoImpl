mkdir -p src/gen/java/org/autoimpl/parser
java -jar lib/jay.jar src/main/jay/JayParser.jay src/main/jay/skeleton > src/gen/java/org/autoimpl/parser/JayParser.java
