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
  * [References](#references)
<!-- TOC -->

## About the project

This project is a personal project to calculate the ELO of the Formula 1 drivers, inspired in the next video of [Mr V's Garage](https://www.youtube.com/live/U16a8tdrbII?t=1046s) and motivated to know who are the greatest of all time. 
<br/>The system will count with 3 different ways to calculate the rating of the drivers: ELO System, iRating system and TrueSkill system.

## Assumptions

- Rookie drivers will start with 1000 ELO/rating
- Indy 500 drivers will be ignored

## Ways of calculating the rating
- [ELO System](docs/elo.md)
- [iRating System](docs/irating.md)
- [TrueSkill System](docs/trueskill.md)

## Milestones

- ✅ **[v1.0.0](https://github.com/Baaarbz/f1-elo/releases/tag/1.0.0) (Dec 1st of 2023):** Publish the first stage of the API with the ELO system implemented.
  - ✅ **[v1.1.0](https://github.com/Baaarbz/f1-elo/releases/tag/1.1.0) (Dec 9th of 2023):** Support for theoretical performance.
  - ✅ **[v1.2.0](https://github.com/Baaarbz/f1-elo/releases/tag/1.2.0) (Dec 9th of 2023):** More features for theoretical performance.
  - ⬜ **v1.3.0 (Dec 9th of 2023):** Reprocess rating system.
- ⬜ v2.0.0: Implement the iRating system.

- ⬜ v3.0.0: Implement the TrueSkill system.

### Future features
- ⬜ Implement in the elo record also information about how much win/lose the driver and against whom.


## References

[Elo Rating System](https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e) <br/>
[Jolpica F1 API](https://github.com/jolpica/jolpica-f1)<br/>
[Mr V's Garage inspiring video](https://www.youtube.com/live/U16a8tdrbII?t=1046s)<br/>
[TrueSkill System](https://www.microsoft.com/en-us/research/project/trueskill-ranking-system/)<br/>
[TrueSkill System Paper](https://www.microsoft.com/en-us/research/wp-content/uploads/2007/01/NIPS2006_0688.pdf)<br/>
[ELO System](https://en.wikipedia.org/wiki/Elo_rating_system)<br/>
[iRating System](https://www.iracing.com/license-progression/) <br/>