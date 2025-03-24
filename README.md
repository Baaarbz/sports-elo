# Sports Elo

[![CD/CI](https://github.com/Baaarbz/f1-elo/actions/workflows/main.yml/badge.svg)](https://github.com/Baaarbz/f1-elo/actions/workflows/main.yml)

> [!NOTE]
> ### Disclaimer
> This is a personal project, I am not affiliated with any sports organization or team. The data used in this project is
> from various public APIs including the Jolpica F1 API for Formula 1. I personally cover the expenses for the server,
> domain, etc. If you like this project and want to support it, you can buy me a coffee.

**A multi-sport ELO rating system to identify the greatest athletes across different sports.**
<!-- TOC -->
* [Sports Elo](#sports-elo)
  * [About the project](#about-the-project)
    * [Formula 1 - Corner cases](#formula-1---corner-cases)
  * [Rating Calculation Methods](#rating-calculation-methods)
    * [Individual 1v1 Sports (Tennis, Boxing, etc.)](#individual-1v1-sports-tennis-boxing-etc)
    * [Team Sports (Football, Basketball, etc.)](#team-sports-football-basketball-etc)
    * [Motorsports (Formula 1, MotoGP, etc.)](#motorsports-formula-1-motogp-etc)
      * [Calculate Expected Finish Probability](#calculate-expected-finish-probability)
      * [Determine the Scaling Factor ($K$-Factor)](#determine-the-scaling-factor-k-factor)
      * [Calculate Actual vs. Expected Performance](#calculate-actual-vs-expected-performance)
      * [Calculate Elo Change](#calculate-elo-change)
      * [Update Elo](#update-elo)
      * [Example Calculation](#example-calculation)
  * [Roadmap](#roadmap)
    * [Nice to have](#nice-to-have)
  * [License Summary](#license-summary)
  * [References](#references)
<!-- TOC -->

## About the project

This project calculates ELO ratings for athletes across various sports, inspired
by [Mr V's Garage](https://www.youtube.com/live/U16a8tdrbII?t=1046s) and motivated by the desire to quantitatively
compare athletes within their respective sports. Initially focused on Formula 1, the system is expanding to include
multiple sports such as MotoGP, NBA, football leagues (Serie A, Premier League, La Liga), boxing, and more. Each sport
uses a rating calculation method appropriate to its competitive structure, allowing for meaningful comparisons within
each discipline.

> [!NOTE]
> All rookie athlete in any sport will start with 1000 ELO>

### Formula 1 - Corner cases

The [Jolpica F1 API](https://github.com/jolpica/jolpica-f1) also retrieves in the first years of F1 Indy 500 races,
those races/drivers will be ignored, and the data will be deleted. The reason is that the Indy 500 was part of the F1
championship in the first years, and the drivers were not the same as the regular F1 drivers.

## Rating Calculation Methods

The system employs different calculation methods based on the type of competition:

### Individual 1v1 Sports (Tennis, Boxing, etc.)

Standard ELO rating system for direct competition:

$$
R'=R+K(S-E)\\
$$
$$
E=\cfrac{Q_A}{(Q_A + Q_B)}\\
$$
$$
Q = 10^{\cfrac{R}{(400)}}
$$

| Parameters | Description                                                                        |
|------------|------------------------------------------------------------------------------------|
| $Q_A$      | $Q_A$ for athlete                                                                  |
| $Q_B$      | $Q_B$ for rival athlete                                                            |
| $R'$       | New athlete rating                                                                 |
| $R$        | Old athlete rating                                                                 |
| $K$        | Multiplier used. Value $K=32$                                                      |
| $S$        | Value depending on the result against the rival ${Win: 1 // Draw: 0.5 // Lose: 0}$ |
| $K(S-E)$   | Rating winnings or losings for athlete                                             |

### Team Sports (Football, Basketball, etc.)

Team average ELO is calculated, then applied to update individual player ratings based on match results

$$ 
R'=R+K(S-E)\\
$$
$$
E=\cfrac{Q_A}{(Q_A + Q_B)}\\
$$
$$
Q = 10^{\cfrac{R}{(400)}}
$$

| Parameters | Description                                                                        |
|------------|------------------------------------------------------------------------------------|
| $Q_A$      | $Q_A$ for team                                                                     |
| $Q_B$      | $Q_B$ for rival team                                                               |
| $R'$       | New team rating                                                                    |
| $R$        | Old team rating                                                                    |
| $K$        | Multiplier used. Value $K=32$                                                      |
| $S$        | Value depending on the result against the rival ${Win: 1 // Draw: 0.5 // Lose: 0}$ |
| $K(S-E)$   | Rating winnings or losings for team (it will be applied to all the team athletes   |

### Motorsports (Formula 1, MotoGP, etc.)
System based in iRating that accounts for position finishing and field strength.


| Variable     | Description                                                                     |
|--------------|---------------------------------------------------------------------------------|
| $R_{before}$ | Driver current iRating before the race                                          |
| $SoF$        | Strength of Field, calculated as the average iRating of all drivers in the race |
| $Position$   | Driver final position in the race                                               |
| $N$          | Number of drivers in the race                                                   |

#### Calculate Expected Finish Probability

_Driver Expected Performance_ against the $SoF$ can be modeled with a probability using the logistic formula:

$$E=\cfrac{1}{1 + 10^\cfrac{SoF - R_{before}}{400}}\\$$

$E$ represents driver expected performance or "win" probability against the field based on his elo relative to the
$SoF$.

#### Determine the Scaling Factor ($K$-Factor)

This factor influences how much driver's elo can change in a single race. For motorsports, a typical $K$-factor
might be set between 30 and 100, depending on field size and competition level. Let’s define a scalable $K$-factor based on the field size:

$$ K=30+\cfrac{70}{N} $$

#### Calculate Actual vs. Expected Performance

Define an Actual Performance Score ($S$) based on your position:

$$ S=1−\cfrac{Position−1}{N−1} $$

This way, the winner gets $S=1$, and the last-place finisher gets $S=0$, with values in between for other positions.

#### Calculate Elo Change

Finally, use the Elo-based adjustment for the elo change:

$$ΔR=K*(S−E)$$

This $ΔR$ is your iRating gain (positive) or loss (negative).

#### Update Elo

New elo:

$$R_{after}=R_{before}+ΔR$$

#### Example Calculation

Let’s say:

1. Driver initial Elo $R_{before}$ is 1500.
   The race $SoF$ is 1600.
   Driver finish in 5th place out of 20 drivers.

2. Expected Finish Probability:
   $$E=\cfrac{1}{1+10\cfrac{(1600−1500)}{400}}=\cfrac{1}{1+10\cfrac{100}{400}}≈0.36$$

3. Scaling Factor ($K$-Factor):
   $$K=30+\cfrac{70}{20}=30+3.5=33.5$$

4. Actual Performance Score ($S$):
   $$S=1−\cfrac{5−1}{20−1}=1−\cfrac{4}{19}≈0.79$$

5. Calculate Elo Change:
   $$ΔR=33.5×(0.79−0.36)=33.5×0.43≈14.4$$

6. Updated Elo:
   $$R_{after}=1500+14.4=1514.4$$

This approximation captures the essence of elo adjustments in iRacing. The parameters (such as $K$-factor) can be
further tuned to match observed iRacing behavior more closely.

## Roadmap
✅ Formula 1
⬜ MotoGP
⬜ Football Leagues: Starting with major European leagues:
      ⬜ La Liga
      ⬜ Premiere League
      ⬜ Serie A
⬜ Basketball (NBA)
⬜ UFC
⬜ Boxing
...

### Nice to have
- ✅ Mechanism to reset and reprocess all the data, been able to reprocess only one rating system. Added in
  version [v2.0.0](https://github.com/Baaarbz/f1-elo/releases/tag/2.0.0)
- ⬜ Implement in the elo record also information about how much win/lose the driver and against whom.
- ⬜ Blue/Green deployment using Docker Swarm.
- ⬜ Take into account DNS and DNF in the calculation of the rating, mechanical issues should not impact negatively to
  motorsports.
- ⬜ Create open source libraries to calculate ratings.

## License Summary

This project is licensed under the MIT License with the addition of a "Commons Clause." Below is a brief summary of what
this means:

* **Permissions**: You are free to use, copy, modify, merge, publish, distribute, sublicense, provided that the original
  copyright notice and this license are included in all copies or substantial portions of the software.
* **Restrictions**: The "Commons Clause" adds a condition that prohibits selling the software itself. This means you
  cannot charge fees for products or services whose primary value comes from the functionality of this software. For
  example, you cannot offer it as a paid hosted service or sell it directly.
* **Warranty Disclaimer**: The software is provided "as is," without warranty of any kind. The authors are not liable
  for any damages or issues arising from its use.

For full details, please refer to the LICENSE file.

## References

[Elo Rating System](https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e) <br/>
[Jolpica F1 API](https://github.com/jolpica/jolpica-f1)<br/>
[Mr V's Garage inspiring video](https://www.youtube.com/live/U16a8tdrbII?t=1046s)<br/>
[ELO System](https://en.wikipedia.org/wiki/Elo_rating_system)<br/>
[iRating System](https://www.iracing.com/license-progression/) <br/>