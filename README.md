### Enhanced DynamoDB client GraalVM compatibility experiment
In dit project worden twee oplossingen geboden om de Enhanced DynamoDB client te laten werken met GraalVM. GraalVM staat niet toe dat de client ```fromBean(Object.class)``` tijdens runtime een schema genereerd.
Daarom zijn er twee oplossingen ontwikkeld die laten zien hoe omgegaan kan worden met dit probleem.

* [MyTableItemStaticSchema](src/main/java/org/shared/model/MyTableItemDynamicSchema.java) definieerd een hard-coded schema. Dit is een oplossing die vereist dat het model op twee plekken bijgewerkt moet worden: De properties van de klasse en de getters en setters van het schema.
* [MyTableItemDynamicSchema](src/main/java/org/shared/model/MyTableItemDynamicSchema.java) defineerd dynamisch een schema door middel van reflectie. Deze vorm van reflectie werkt wél met GraalVM. Write-once oplossing.

Dit project laat zien dat GraalVM bepaalde implementaties en refactors forceert. GraalVM gebruiken is niet vrijblijvend.

### Deployment
Om deze applicatie te deployen moeten de volgende commands uitgevoerd worden:
```
mvn clean package -Pnative
sam deploy
```