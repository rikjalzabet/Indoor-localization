import { Component, OnInit } from '@angular/core';
import { WebUiService } from '../services/web-ui.service';
import { IFloorMap } from '../models/IFloorMap';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IAssetPositionHistory } from '../models/IAssetPositionHistory';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [ MatFormFieldModule,
    MatSelectModule,
    FormsModule,
  CommonModule,],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css'
})
export class ReportsComponent implements OnInit{
  floorMaps? : IFloorMap[];
  floorMapMap = new Map<number, string>();
  selectedFloorMapId?: number;
  assetPositionHistory? : IAssetPositionHistory[];
  constructor(private webUiService: WebUiService){}

  ngOnInit(): void {
    this.fetchFloorMaps();
  }

  fetchFloorMaps(): void {
    this.webUiService.getFloorMaps().subscribe({
      next: (data) => {
        this.floorMaps = data;
        this.floorMaps.forEach(fm => this.floorMapMap.set(fm.id, fm.name));
      },
      error: (err) => console.error('Error fetching data', err)
    });
  }

  getFloorMapImage(floorMapId?: number): string | undefined {
    if (floorMapId === undefined) {
      return undefined;
    }
    const floorMap = this.floorMaps?.find((fm) => fm.id === floorMapId);
    this.fetchAssetPositionHistory(floorMapId);
    return floorMap?.image;
  }

  fetchAssetPositionHistory(floorMapId : number): void{
    this.webUiService.getAssetPositionHistory(floorMapId).subscribe({
      next: (data) => {
        this.assetPositionHistory = data;
      },
      error: (err) => console.error('Error fetching data', err)
    });
    console.log(this.assetPositionHistory);
  }

}
