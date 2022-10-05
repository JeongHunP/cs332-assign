# The Mythical Man Month Chapter 2 Summary

# 2. The Mythical Man-Month

## Optimism
대부분의 프로그래머들은 지나치게 낙관적인데, 그 프로그램의 작성에 걸려야할 시간만큼만 걸릴 것이고 모든 것은 잘 될 것이라고 생각한다. 물론 아무 delay 없이 잘 될 가능성도 존재하겠지만 실제 구현에 들어가면 끝없는 연쇄된 작업들이 있으며 위의 확률은 굉장히 희박하다.

## The Man-Month
man - month가 interchangeable하려면 task가 분할되면서 서로의 소통이 필요 없을 때이다. 프로그래밍의 경우 sequential task의 경우 분리될 수 없으며 이는 schedule에 크게 영향을 미치지 않는다. 또한 소통이 필요한 경우 추가적인 work이므로 men과 month를 trade하더라도 더 output이 나쁠 수 있다. communication의 추가적인 업무는 training과 intercommunication으로 구성된다. training은 개개인이 직접 해야하므로 n에 선형이고, intercommunication은 n(n-1)/2이므로 더더욱 심각한 burden을 낳는다. 즉, 단순히 men을 더 투입하는 것은 schedule을 더 길게 만드는 효과를 가진다.

## Systems Test
sequential constraints보다 component debugging과 system test에 더 많은 시간이 들어간다. 프로그래머들이 낙관적이기 때문에 여기에 들어갈 시간이 거의 0에 수렴한다고 생각하지만, 가장 mis-scheduled되는 곳이 testing이다. 저자는 1/3 planniing, 1/6 codding, 1/4 component test and early system test, 1/4 system test, all components in hand로 구성했다고 한다. system test에 들어가는 시간을 잘못 계산하게 되면, 마지막에 다다라서야 schedule이 지연되고 이를 알아차리게 되었을 때는 이미 늦은 경우가 많다. 

## Gutless Estimating
프로그래머들이 고객의 due date를 맞추기 위해서 엄격한 scheduling도 필요하겠지만, 만약 그것이 힘들 경우에는 널널하지만 오래걸리는 시간이 희망만주는 빠른 due date 약속보다 낫다.

## Regenerative Schedule Disaster
만약 주어진 시간에 끝내지 못하는 경우, man을 더 추가하는 것, rescheduling, trim the task 등의 방법이 있다. man-month를 계산하여 남은 양만큼 man을 추가하는 것은 예상외로 더 많은 시간이 걸리는데, 그 man들이 아무리 빨리 구해지고 능력이 있더라도 기존 task를 train할 시간이 필요하며 task를 분할 하는 것도 기존의 사람 수에서 더 추가해서 분배해야한다. 즉, "Adding manpower to a late software project makes it later"라는 말이다. 