# Clean Code Chapter 3 Summary

# 3. Functions

## Small!
blocks, indenting 등을 잘 이용하여 function 자체는 항상 짧고 간결할수록 좋다.

## Do One Thing
함수는 하나의 일만 확실하게 하는 것이 좋다. 여러가지 기능들이 섞이면 이후에 수정하기도 불편하고 에러가 발생했을 때 처리하기 힘들다.

## One Level of Abstraction per Function
함수 안에 있는 statement가 같은 level의 abstraction을 가지는 것이 좋다. 

## Switch Statements
polymorphism을 이용해서 low level class에 긴 switch문을 두자. 

## Use Descriptive Names
descriptive names을 사용하는 것은 design을 더 명확하게 해준다.

## Function Arguments
인자가 많아지면 매번 함수를 볼 때마다 해석해야하고 테스트하는데도 있어 불편함을 줄 수 있다. 함수 인자로는 없거나 하나만 넘겨주는 것이 이상적이다.

## Have No Side Effects
하나만 하게 함수를 설계해도 예상치 못한 숨겨진 변화가 있을 수 있다. 이는 전체 코드에 심각한 영향을 미칠 수도 있으므로 특정 상황을 잘 체크해야한다. 

## Command Query Separation
함수는 무언가를 하거나 무언가를 답변해야하는데, 동시에는 아니다. `set`과 `get`을 동시에 하게 된다면 혼란을 야기할 수 있다. 

## Prefer Exceptions to Returning Error Codes
에러 코드를 반환하는것 보다는 exception을 사용하는 것이 더 좋다. 예를 들어서, `try/catch` block을 만들어서 이를 처리할 수 있다. 이외에도 error handling은 중요한 요소이다.

## Don't Repeat Yourself
duplication은 다른 코드들과 섞이면 찾기 힘드므로 `include` 방법을 이용해서 처리할 수 있다. 

## How Do You Write Functions Like This?
코드를 짜는 것은 마치 글을 쓰는 것과 같으므로 잘 읽힐때 까지 restructure을 하고 refine 해야한다.





