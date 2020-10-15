# Readme

This module patches the swagger codegen module so that it can build on windows addressing the following issue. If the issue is fixed in swagger, this will no longer be neccessary.

https://github.com/swagger-api/swagger-codegen/issues/9045

 JAVA-JAXRS-server Windows Mixing of path separators causes ..ServiceImpl.java to be generated in wrong directory #9045
 
The patch provided is 

![wrong_path_patch.txt](../swagger-codegen-jaxrs-patch/doc/wrong_path_patch.txt ")