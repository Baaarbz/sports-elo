from abc import ABC, abstractmethod
from domain.driver import Driver

class DriverRepository(ABC):
    @abstractmethod
    def save(self, driver: Driver):
        raise NotImplementedError