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
    <li>
      <a href="#considerations">Considerations</a>
    </li>
    <li>
      <a href="#references">References</a>
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
$R$ old driver rating <br/>
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

**Solutions**:
> **Option A**:
> <br/>The drivers will get wins and loses depends on the final position, accumulating the loses or the winnings in
> terms of ELO, for example: <br/>
> There are 4 drivers for a team in the same race, driver A, B, C and D end the race in the respective positions (1º A,
> 2º B, 3º C, 4º D)
> each driver wins and loses the same ELO, for example 10. Then the result will end as:
>    - Driver A: Wins $+30$ ELO (wins against 3 drivers $+10 +10 +10 = 30$ )
>    - Driver B: Wins $+10$ ELO (wins against 2 drivers $+10 +10 = 20$ but lose against 1 driver $-10 = -10$)
>    - Driver C: Lose$-10$ ELO (wins against 1 driver $+10= 10$ but loses against 2 drivers $-10-10 = -20$)
>    - Driver D: Lose $-30$ ELO (loses against 3 drivers $-10 -10-10 = -30$ )
> ________
> **Issues**:
> <br/>The problem with this solution is that the drivers will get the ELO calculated based on the ELO previous of the
> race, in case they win, they will win more ELO, and in case they lose, they will lose more ELO, and this is not fair.
> And this case does not happen in real world, if you win/lose in your next match you will get your ELO updated in base
> of
> the last record of elo you have not based in a fixed one.
> ____
> **Conclusion**:
> <br/>After several test we have concluded that drivers before 1981 have in average more chances to win and lose "a
> lot" more ELO than modern drivers (post 1981) and breaks the consistency of the ELO system, so we are going to discard
> this option

> **Option B**:
> <br/>Use the same approach as before but the $K$ multiplier will be divided by the number of cars racing in the team.

<br/>

#### $N$ drivers share the same car in the race (Argentina GP 1955...)

> **Solution**:
> <br/> We will take the average ELO of that car, and it will be used to calculate the new rating, for example: <br/>
> There are 3 drivers for a team of 2 cars, driver A & B share the same car, driver C has his own car:
>   - Driver A & B: calculate avg of ELO, and we calculate wins and loses as if they were the same driver, and the total
      o wins and loses will be divided by 2 and then aggregated to each driver
>   - Driver C: calculate wins and loses as normal


<br/>

#### Driver race for multiple teams in the same weekend (1978 Italian GP)

> **Solution**:
> <br/> He will get ELO updated for each team


<br/>

#### Indie 500

> **Solution**:
> <br/> Ignore it

_________________

## Iterations

### Take into account the performance of the car

For both possible solutions we require to calculate the elo, only when the season ends, as we need to know the final
result of the constructors standings.
Also, due to the lack of consistency in the first seasons of Formula 1 in terms of, teams, number of drivers per team,
and so on, we are going to take into account for this feature, only since season 1981, were each team can only have 2
drivers.
There are two options:

> **Option A**: <br/>
> Add a new multiplier to $R'=R+K(S-E)$ as for example: $R'=R+K(S-E)*TR$ where $TR$ is **T**eam **R**ating<br/>

> **Option B**: <br/>
> Increase/decrease the value of $S$ depending on if the driver is performing above or under the car performance
> $R'=R+K((S+TP)-E)$
> <br/>Where TP is **T**heorical **P**erformance those values of TP will be based on the next table:

|     | 1º - 2º<br/>1º in standings | 3º - 4º<br/>2º in standings | 5º - 6º<br/>3º in standings | 7º - 8º<br/>4º in standings | 9º - 10º<br/>5º in standings |
|-----|-----------------------------|-----------------------------|-----------------------------|-----------------------------|------------------------------|
| 1º  | 0                           | 0.2                         | 0.4                         | 0.6                         | 0.8                          |
| 2º  | 0                           | 0.1                         | 0.3                         | 0.5                         | 0.7                          |
| 3º  | -0.1                        | 0                           | 0.2                         | 0.4                         | 0.6                          |
| 4º  | -0.2                        | 0                           | 0.1                         | 0.3                         | 0.5                          |
| 5º  | -0.3                        | -0.1                        | 0                           | 0.2                         | 0.4                          |
| 6º  | -0.4                        | -0.2                        | 0                           | 0.1                         | 0.3                          |
| 7º  | -0.4                        | -0.3                        | -0.1                        | 0                           | 0.2                          |
| 8º  | -0.5                        | -0.4                        | -0.2                        | 0                           | 0.1                          |
| 9º  | -0.6                        | -0.5                        | -0.3                        | -0.1                        | 0                            |
| 10º | -0.7                        | -0.6                        | -0.4                        | -0.2                        | 0                            |

### Calculate the ELO of the constructors

TBD

_________________

## References

[Elo Rating System](https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e) <br/>
[Jolpica F1 API](https://github.com/jolpica/jolpica-f1)<br/>
[Mr V's Garage inspiring video](https://www.youtube.com/live/U16a8tdrbII?t=1046s)<br/>