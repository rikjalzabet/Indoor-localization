import { Component, OnInit } from '@angular/core';
import { IFloorMap } from '../models/IFloorMap';
import { WebUiService } from '../services/web-ui.service';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { IAsset } from '../models/iasset';
import { MatIcon,MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule,
    MatButtonModule,
    MatListModule,
    MatIconModule,
    MatTooltipModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  floorMaps? : IFloorMap[];
  floorMapMap = new Map<number, string>();
  selectedFloorMapId?: number;
  assets? : IAsset[];

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
    this.getAssets(floorMapId);
    return floorMap?.image;
  }

  getAssets(floorMapId?: number): void {
    this.webUiService.getAssets().subscribe({
      next:(data)=>{
        this.assets = data.filter(asset => asset.floorMapId == floorMapId );
      },
      error: (err) => console.error('Error fetching data', err)
    })
    console.log("ASSETI:" , this.assets);
  }

  selectFloorMap(floorMapId: number): void {
    this.selectedFloorMapId = floorMapId;
  }

  trackByAssetId(index: number, asset: IAsset): number {
    return asset.id; 
  }
}
