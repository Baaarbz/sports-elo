# F1 Elo

> ### Disclaimer
> This is a personal project, I am not affiliated with Formula 1 or any of its teams. The data used in this project is
> from the [Jolpica F1 API](https://github.com/jolpica/jolpica-f1). I run personally with the expenses of the server,
> domain and so on. If you like this project and want to support it, you can buy me a coffee.

**Formula 1 ELO system. Who are the GOATs? Let's see!**

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About the project</a>
    </li>
    <li>
      <a href="#how-to-calculate-it">How to calculate it</a>
    </li>
    <li>
      <a href="#assumptions">Assumptions</a>
      <ul>
        <li><a href="#corner-cases">Corner cases</a></li>
      </ul>
    </li>
    <li>
      <a href="#iterations">Iterations</a>
      <ul>
        <li><a href="#take-into-account-the-performance-of-the-car">Take into account the performance of the car</a></li>
      </ul>
    </li>
  </ol>
</details>

_________________

## About the project

This project is a personal project to calculate the ELO of the Formula 1 drivers, inspired in the next video
of [Mr V's Garage](https://www.youtube.com/live/U16a8tdrbII?t=1046s). <br/>The ELO system is a method for
calculating the relative skill levels of players in two-player games such as chess. It is named after its creator Arpad
Elo, a Hungarian-American physics professor. In this case, there are some assumptions to make the ELO system work in the
Formula 1 world.
As the drivers does not compete with equal machinery we are going to evaluate the drivers against their teammates, at
least for the first stage of the project.

_________________

## How to calculate it

$$
R'=R+K(S-E)\\
$$
$$
E=\cfrac{Q_A}{(Q_A + Q_B)}\\
$$
$$
Q = 10^{\cfrac{R}{(400)}}
$$

$Q_A$ for driver <br/>
$Q_B$ for teammate <br/>
$R'$ new driver rating <br/>
$R$  old driver rating <br/>
$K$ is a multiplier we will use $K=32$ the double than chess.com <br/>
$S$ Result against teammate <br/>

| Result | $S$ |
|--------|-----|
| Win    | 1   |
| Draw   | 0.5 |
| Lose   | 0   |


$K(S-E)$ Rating winnings or losings for driver <br/>
_________________
## Assumptions

- Rookie drivers will start with 1000 ELO

### Corner cases

#### More than 2 drivers per team (Argentina GP 1955...)

> **Solution**: 
> <br/>The drivers will get wins and loses depends on the final position, accumulating the loses or the winnings in
> terms of ELO, for example: <br/>
> There are 4 drivers for a team in the same race, driver A, B, C and D end the race in the respective positions (1º A, 2º B, 3º C, 4º D)
> each driver wins and loses the same ELO, for example 10. Then the result will end as:
>    - Driver A: Wins $+30$ ELO (wins against 3 drivers $+10 +10 +10 = 30$ )
>    - Driver B: Wins $+10$ ELO (wins against 2 drivers $+10 +10 = 20$  but lose against 1 driver $-10 = -10$)
>    - Driver C: Lose$-10$ ELO (wins against 1 driver $+10= 10$  but loses against 2 drivers $-10-10 = -20$)
>    - Driver D: Lose $-30$ ELO (loses against 3 drivers $-10 -10-10 = -30$ )

<br/>

#### $N$ drivers share the same car in the race (Argentina GP 1955...)
> **Solution**:
> <br/> We will take the average ELO of that car and they will win or lose the same amount of ELO, for example: <br/>
> There are 3 drivers for a team of 2 cars, driver A & B share the same car, driver C has his own car:    
>   - Driver A & B: calculate avg of ELO, and we calculate wins and loses as if they were the same driver
>   - Driver C: calculate wins and loses as normal


<br/>

#### Driver race for multiple teams in the same weekend (1978 Italian GP)
> **Solution**:
> <br/> Ignore it


<br/>

#### Indie 500
> **Solution**:
> <br/> Ignore it

_________________

## Iterations
### Take into account the performance of the car (WIP)
> **Option A**: <br/>
>Add a new multiplier to  $R'=R+K(S-E)$  as for example: $R'=R+K(S-E)*TR$ where $TR$ is **T**eam **R**ating, tradeoffs:<br/>
>    - ELO only can be calculated at the end of the season.
>    - It should be an ELO if you win against teammate and other if you lose, for example: you have the worst car but
>      you end 1º and 2º in the race, you should not lose ELO, or at least you should not lose a lot, hard to fix
>      this case (to not lose or win ELOif you end in front of other cars…)
>    - If you have the 10th car and you en 8th when you expected result is 19th and 20th you should win a lot more

> **Option B**: <br/>
> Increase/decrease the value of $S$ depending if the driver is performing above or under the car performance
> - $R'=R+K((S+TP)-E)$
> - Where TP is theorical performance
> - The values of TP will be bases on:
>     - If you finish last with the best car you should be penalized as a lost $TP = -1$
>     - If you finish first with the best car nothing should change $TP= 0$
>     - And the same but inversed for the worst car
> - This makes more sense than multiplying randomly, if the driver is performing better than the car therically
>   performance will ve rewarded but, if is underperforming then will be penalized
> - If the driver end as DNF, we dont know if was by his mistake (should get full penalization) or mechanical
>   failure (not deserve full penalization) then, in this case, this new value called $TP$ well be equals to 0
> - In the table bellow well be shown how this will be calculated, X axis = theorical position Y axis = driver
>   position at race

  |  | 1º | 2º | 3º | 4º | 5º | 6º | 7º | 8º | 9º | 10º | 11º | 12º | 13º | 14º | 15º |
                  | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
  | 1º | 0 | 0 |  |  |  |  |  |  |  |  |  |  |  |  |  |
  | 2º | 0 | 0 |  |  |  |  |  |  |  |  |  |  |  |  |  |
  | 3º |  |  | 0 | 0 |  |  |  |  |  |  |  |  |  |  |  |
  | 4º |  |  | 0 | 0 |  |  |  |  |  |  |  |  |  |  |  |
  | 5º |  |  |  |  | 0 | 0 |  |  |  |  |  |  |  |  |  |
  | 6º |  |  |  |  | 0 | 0 |  |  |  |  |  |  |  |  |  |
  | 7º |  |  |  |  |  |  | 0 | 0 |  |  |  |  |  |  |  |
  | 8º |  |  |  |  |  |  | 0 | 0 |  |  |  |  |  |  |  |
  | 9º |  |  |  |  |  |  |  |  | 0 | 0 |  |  |  |  |  |
  | 10º |  |  |  |  |  |  |  |  | 0 | 0 |  |  |  |  |  |
  | 11º |  |  |  |  |  |  |  |  |  |  | 0 | 0 |  |  |  |
  | 12º |  |  |  |  |  |  |  |  |  |  | 0 | 0 |  |  |  |
  | 13º |  |  |  |  |  |  |  |  |  |  |  |  | 0 | 0 |  |
  | 14º |  |  |  |  |  |  |  |  |  |  |  |  | 0 | 0 |  |
  | 15º |  |  |  |  |  |  |  |  |  |  |  |  |  |  | 0 |

## References

[Elo Rating System](https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e)
[Jolpica F1 API](https://github.com/jolpica/jolpica-f1)
[Mr V's Garage inspiring video](https://www.youtube.com/live/U16a8tdrbII?t=1046s)