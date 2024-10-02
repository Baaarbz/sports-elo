from dataclasses import dataclass, field
from datetime import date
from typing import List
    
@dataclass(frozen=True)
class Elo:
    elo: int
    date: date   
    
@dataclass(frozen=True)
class DriverId:
    value: str = field(init=True)
    
    def __post_init__(self):
        if not self.value:
            raise ValueError("DriverId value cannot be empty.")
    
@dataclass(frozen=True)
class DriverName:
    value: str = field(init=True)
    
    def __post_init__(self):
        if not self.value:
            raise ValueError("DriverName value cannot be empty.")
    
@dataclass(frozen=True)
class Driver:
    id: DriverId
    name: DriverName
    current_elo: Elo
    elo_record: List[Elo] = field(default_factory=list)
    
    def highest_elo(self) -> Elo:
        if not self.elo_record:
            raise ValueError("No Elo records available.")
        return max(self.elo_record, key=lambda e: e.elo)
    
    def lowest_elo(self) -> Elo:
        if not self.elo_record:
            raise ValueError("No Elo records available.")
        return min(self.elo_record, key=lambda e: e.elo)
    
    def update_elo(self):
        raise NotImplementedError