# Clean Code Chapter 2 Summary

# 2. Meaningful Names

## Use Intention-Revealing Names
의도가 드러나는 변수명을 짓는 것은 굉장히 중요하다. 좋은 이름을 지음으로써 그 변수 또는 함수가 무엇을 하는지 쉽게 파악이 가능하며 이는 시간을 아껴줄 뿐만 아니라 전반적인 코드를 이해하는 데에도 큰 도움을 준다.

## Avoid Disinformation
코드의 의미를 헷갈리게 할 수 있는 단서를 최대한 없애야한다. 
예를들어, `accountList`와 같이 실제 `List`가 아닌데 단지 account의 그룹이라는 의미로 이름을 짓지 말아야 한다는 것이다. 
또한, `XYZControllerForEfficientHandlingOfStrings`와 `XYZControllerForEfficientStorageOfStrings` 같이 많은 정보를 주는 것은 좋지만 이것들끼리 헷갈리지 않게 정보를 적절하게 표현해야한다.

## Make Meaningful Distinctions
같은 범위 안에서 비슷한 종류의 naming이 필요할 때, 숫자나 의미없는 단어를 붙이기 보다는 명확한 이름을 짓자. `a1`, `a2` ... 보다 `source`, `destination`이 더 이 이해하는 데에 낫다.

```c++
getActiveAccount();
getActiveAccounts();
getActiveAccountInfo();
```
와 같은 상황에서는 account의 정보를 얻기 위해 어떤 함수를 써야하는지 알 수가 없다. 구체적이고 명확한 정보를 전달하자.

## Use Pronounceable Names
발음하기 쉬운 이름을 짓자. 
```c++
class DtaRcrd102 {
private Date genymdhms;
private Date modymdhms;
private final String pszqint = "102";
/* ... */
};
```
는 곧
```c++
class Customer {
private Date generationTimestamp;
private Date modificationTimestamp;;
private final String recordId = "102";
/* ... */
};
```
으로 바꿈으로써 명확한 의사소통이 가능하다.

## Use Searchable Names
검색하기 쉬운 이름을 사용하자. 

## Avoid Encodings
쓸데없는 encoded names은 발음하기 쉽지 않을뿐만 아니라 더 많은 부담을 지어준다. 

## Avoid Mental Mapping
명료한 것이 좋으므로 이미 이해하고 있는 내용을 또 다시 바꾸어서 추가적인 부담을 지을 필요가 없다.

## Class Names / Method Names
class 이름은 흔하지 않은 명사가 좋고, method 이름은 동작이 확실한 동사가 좋다.

## Don't Be Cute
누구나 이해할 수 있는 명확한 단어를 사용하자. 문화나 특정 집단에서 이해할 수 있는 농담이나 slang은 이해에 불필요하다.

## Pick One Word Per Concept
같은 컨셉을 가지고 있는 이름에는 하나의 단어만 붙이자. `fetch` `retrive` `get`과 같이 비슷한 단어는 헷갈리게 한다.

## Don't Pun
같은 단어를 다른 목적에 사용하지 마라. 

## Use Solution Domain Names
코드를 읽는 사람들은 대부분 프로그래머일 확률이 높으므로 cs terms와 같이 technical terms를 사용하라.

## Use Problem Domain Names
problem/solution domain을 나누어서 사용하라,

## Add Meaningful Context
변수들을 해당 scope에서 문맥에 맞는 용어들을 사용하라. 이름만 가지고도 파악이 될 뿐더러 그 문맥에 맞지 않으면 그 이름이 무엇을 의미하는지 유추하기 어렵다.

## Don't Add Gratuitous Context
비슷한 문맥의 단어들이 있다면 이 역시 구분하여 사용하자.