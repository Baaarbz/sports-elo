# F1 Elo

[![CD/CI](https://github.com/Baaarbz/f1-elo/actions/workflows/main.yml/badge.svg)](https://github.com/Baaarbz/f1-elo/actions/workflows/main.yml)

> [!NOTE]
> ### Disclaimer
> This is a personal project, I am not affiliated with Formula 1 or any of its teams. The data used in this project is
> from the [Jolpica F1 API](https://github.com/jolpica/jolpica-f1). I run personally with the expenses of the server,
> domain and so on. If you like this project and want to support it, you can buy me a coffee.

**Formula 1 ELO system. Who are the GOATs? Let's see!**
<!-- TOC -->
* [F1 Elo](#f1-elo)
  * [About the project](#about-the-project)
  * [Assumptions](#assumptions)
  * [Ways of calculating the rating](#ways-of-calculating-the-rating)
  * [Milestones](#milestones)
    * [Nice to have](#nice-to-have)
  * [License Summary](#license-summary)
  * [References](#references)
<!-- TOC -->

## About the project

This project is a personal project to calculate the ELO of the Formula 1 drivers, inspired in the next video of [Mr V's Garage](https://www.youtube.com/live/U16a8tdrbII?t=1046s) and motivated to know who are the greatest of all time. 
<br/>The system will count with 3 different ways to calculate the rating of the drivers: ELO System, iRating system and TrueSkill system.

## Assumptions

- Rookie drivers will start with 1000 ELO/rating
- Indy 500 drivers will be ignored
- If we don't have the data analysed of a season with the theoretical performance of the car, ~~we will use the World Constructors' Championship as a reference, applying a difference of 0.2s per car.~~ we will set the theoretical performance to 0

## Ways of calculating the rating
- [ELO System](docs/elo.md)
- [iRating System](docs/irating.md)
- [TrueSkill System](docs/trueskill.md)

## Milestones

- ✅ **[v1.0.0](https://github.com/Baaarbz/f1-elo/releases/tag/1.0.0) (Dec 1st of 2023):** Publish the first stage of the API with the ELO system implemented.
- ⬜ v2.0.0: Implement the iRating system.
- ⬜ v3.0.0: Implement the TrueSkill system.

### Nice to have
- ✅ Support for theoretical performance. Added in minor versions [1.1.0](https://github.com/Baaarbz/f1-elo/releases/tag/1.1.0) and [1.2.0](https://github.com/Baaarbz/f1-elo/releases/tag/1.2.0)
- ⬜ Mechanism to reset and reprocess all the data, been able to reprocess only one rating system.
- ⬜ Implement in the elo record also information about how much win/lose the driver and against whom.
- ⬜ Do not save in database Indy 500 drivers and delete relative data.
- ⬜ Blue/Green deployment using Docker Swarm.
- ⬜ Improvements in performance (optimization of SQL queries and code process).
- ⬜ Take into account DNS and DNF in the calculation of the rating, mechanical issues should not impact negatively to drivers.
- ⬜ Create open source libraries to calculate ratings.

## License Summary

This project is licensed under the MIT License with the addition of a "Commons Clause." Below is a brief summary of what this means:

* **Permissions**: You are free to use, copy, modify, merge, publish, distribute, sublicense, provided that the original copyright notice and this license are included in all copies or substantial portions of the software.
* **Restrictions**: The "Commons Clause" adds a condition that prohibits selling the software itself. This means you cannot charge fees for products or services whose primary value comes from the functionality of this software. For example, you cannot offer it as a paid hosted service or sell it directly.
* **Warranty Disclaimer**: The software is provided "as is," without warranty of any kind. The authors are not liable for any damages or issues arising from its use.

For full details, please refer to the LICENSE file.

## References

[Elo Rating System](https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e) <br/>
[Jolpica F1 API](https://github.com/jolpica/jolpica-f1)<br/>
[Mr V's Garage inspiring video](https://www.youtube.com/live/U16a8tdrbII?t=1046s)<br/>
[TrueSkill System](https://www.microsoft.com/en-us/research/project/trueskill-ranking-system/)<br/>
[TrueSkill System Paper](https://www.microsoft.com/en-us/research/wp-content/uploads/2007/01/NIPS2006_0688.pdf)<br/>
[ELO System](https://en.wikipedia.org/wiki/Elo_rating_system)<br/>
[iRating System](https://www.iracing.com/license-progression/) <br/>