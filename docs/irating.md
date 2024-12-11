# iRating System

> [!IMPORTANT]
> iRacing does not publicly release its exact iRating formula, but we can approximate the system with a similar
> approach based on the Elo rating model with adjustments for position-based rankings, field size, and Strength of
> Field (SoF). Here’s a version of the formula that should give us reasonable results for the project.

## How to calculate it - Step by Step

### Variables definition

| Variable     | Description                                                                     |
|--------------|---------------------------------------------------------------------------------|
| $R_{before}$ | Driver current iRating before the race                                          |
| $SoF$        | Strength of Field, calculated as the average iRating of all drivers in the race |
| $Position$   | Driver final position in the race                                               |
| $N$          | Number of drivers in the race                                                   |

### Calculate Expected Finish Probability

_Driver Expected Performance_ against the $SoF$ can be modeled with a probability using the logistic formula:
$$E=\cfrac{1}{1 + 10^\cfrac{SoF - R_{before}}{400}}\\$$
$E$ represents driver expected performance or "win" probability against the field based on his iRating relative to the
$SoF$.

### Determine the Scaling Factor ($K$-Factor)

This factor influences how much driver's iRating can change in a single race. For motorsports, a typical $K$-factor
might be
set between 30 and 100, depending on field size and competition level.
Let’s define a scalable $K$-factor based on the field size:
$$ K=30+\cfrac{70}{N} $$

### Calculate Actual vs. Expected Performance

Define an Actual Performance Score ($S$) based on your position:
$$ S=1−\cfrac{Position−1}{N−1} $$
This way, the winner gets $S=1$, and the last-place finisher gets $S=0$, with values in between for other positions.

### Calculate iRating Change

Finally, use the Elo-based adjustment for the iRating change:
$$ΔR=K*(S−E)$$
This $ΔR$ is your iRating gain (positive) or loss (negative).

### Update iRating

New iRating:
$$R_{after}=R_{before}+ΔR$$

## Example Calculation

Let’s say:

1. Driver initial iRating $R_{before}$ is 1500.
   The race $SoF$ is 1600.
   Driver finish in 5th place out of 20 drivers.

2. Expected Finish Probability:
   $$E=\cfrac{1}{1+10\cfrac{(1600−1500)}{400}}=\cfrac{1}{1+10\cfrac{100}{400}}≈0.36$$

3. Scaling Factor ($K$-Factor):
   $$K=30+\cfrac{70}{20}=30+3.5=33.5$$

4. Actual Performance Score ($S$):
   $$S=1−\cfrac{5−1}{20−1}=1−\cfrac{4}{19}≈0.79$$

5. Calculate iRating Change:
   $$ΔR=33.5×(0.79−0.36)=33.5×0.43≈14.4$$

6. Updated iRating:
   $$R_{after}=1500+14.4=1514.4$$

This approximation captures the essence of iRating adjustments in iRacing. The parameters (such as $K$-factor) can be
further tuned to match observed iRacing behavior more closely.

## Taking into account the _Theoretical Performance of the car_

To account for the theoretical performance of each car in a formula like iRating, we can adjust the expected
performance ($E$) to include the handicap due to machinery. Here's how we are going to integrate this adjustment:

### Incorporating Car Performance Handicap

New Variables:

| Variable       | Description                                                                                                                                                                     |
|----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| $CarPerf$      | The theoretical performance of the car. The fastest car has $CarPerf=0$, and slower cars have positive values representing the time (in seconds) they are theoretically slower. |
| $AdjustedPerf$ | An adjusted theoretical performance score for each driver, used to calculate their relative competitiveness.                                                                    |


### Adjusting Expected Performance ($E$):

To account for car performance, we are going to modify the Expected Finish Probability formula:
$$AdjustedPerf=R_{before}−(α×CarPerf)$$

Where $α$ is a scaling factor to convert the time deficit (in seconds) into an iRating penalty. For example, if $α=50$, a car that is 0.5 seconds slower would lose $0.5×50=250$ iRating points in its adjusted rating.

Then, use the adjusted iRating in the logistic formula:
$$E=\cfrac{1}{1+10^\cfrac{SoF−AdjustedPerf}{400}}$$

This means slower cars will have a lower _Expected Performance_ $E$, accounting for the theoretical disadvantage.



## Example adjusted calculation:
Scenario:

- Driver A: $R_{before}=1500$ $CarPerf=0$(fastest car).
- Driver B: $R_{before}=1500$ $CarPerf=0.5$ (0.5 seconds slower).
- $α=50$: Time penalty conversion factor.
- $SoF=1550$ 
- $N=20$.

Steps:

1. Adjusted Performance:
   - Driver A: $$AdjustedPerf_A=1500−50×0=1500$$
   - Driver B: $$AdjustedPerf_B=1500−50×0.5=1475$$

2. Expected Finish Probability:
   - Driver A:
              $$E_A=\cfrac{1}{1+10^\cfrac{(1550−1500)}{400}}≈0.36$$
   - Driver B:
              $$E_B=\cfrac{1}{1+10^\cfrac{1550−1475}{400}}≈0.29$$

3. Actual Performance (SS):
   - Assume Driver A finishes 1st ($S_A=1$).
   - Assume Driver B finishes 10th ($S_B=1−10−120−1≈0.53$).

4. iRating Change:
        $$K=30+\cfrac{70}{20}=33.5$$
   - Driver A:
        $$ ΔR_A=33.5×(1−0.36)≈21.5$$ 
   - Driver B:
        $$ΔR_B=33.5×(0.53−0.29)≈8.0$$

5. New iRating:
   - Driver A: $$R_{A after}=1500+21.5=1521.5$$
   - Driver B: $$R_{B after}=1500+8.0=1508.0$$

## Summary:

By incorporating the $CarPerf$ handicap into the formula:

Slower cars are given a lower expectation ($E$), so finishing above expectations rewards them more. Faster cars face a higher $E$, meaning they need to win or perform well to avoid losing iRating.

This approach balances performance differences due to machinery while preserving competitiveness.