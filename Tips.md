### Problems, Solutions & Root-Cause during the developing  
+ Lombok warning in inheritance classes.  
Lombok warning happens in the condition that I have a subClass and a class in codes while running the application.
 !Warning:(18, 1) java: Generating equals/hashCode implementation but without a call to superclass, even though this class does not extend java.lang.Object. If this is intentional, add '@EqualsAndHashCode(callSuper=false)' to your type.  
 
[Solutions: Add @EqualsAndHashCode annotation for the subClass](https://stackoverflow.com/questions/38572566/warning-equals-hashcode-on-data-annotation-lombok-with-inheritance)


+ Maybe the best way to define constants var in java.  
<b>What is the best way to implement constants in Java?</b>   
Avoiding this pattern even has its own item (#18) in Bloch's Effective Java if define constants var in interface. An argument Bloch makes against the constant interface pattern is that use of constants is an implementation detail, but implementing an interface to use them exposes that implementation detail in your exported API.  
 [Solution: Define constants var in Enum in java](https://stackoverflow.com/questions/66066/what-is-the-best-way-to-implement-constants-in-java)


- [How to handle with exception](https://howtodoinjava.com/best-practices/java-exception-handling-best-practices/)
  
- [@Value is null](https://stackoverflow.com/questions/4130486/spring-value-annotation-always-evaluating-as-null)

- [Find out CurrentRunningMethod in java](https://stackoverflow.com/questions/442747/getting-the-name-of-the-currently-executing-method)

- [MD5](https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string)

- [@Id difference between javax.persistence.Id and org.springframework.data.annotation.Id](https://stackoverflow.com/questions/39643960/whats-the-difference-between-javax-persistence-id-and-org-springframework-data)

- [Mybatis-Batch-Operations](http://www.mybatis.org/mybatis-3/sqlmap-xml.html)

- [RegExp (datetime from text-string)](https://stackoverflow.com/questions/18591242/java-extract-date-from-string-using-regex-failing)

- [RegExp (detects and extract url from long text-string)](https://stackoverflow.com/questions/5713558/detect-and-extract-url-from-a-string)

- [Mybatis. Mapper.xml mapping issues for resultMap of Class/SubClass](https://stackoverflow.com/questions/49328763/mybatis-how-to-map-a-class-property-stored-in-same-table-as-parent-class)



