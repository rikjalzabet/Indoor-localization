import { Component, OnInit } from '@angular/core';
import { IFloorMap } from '../models/IFloorMap';
import { WebUiService } from '../services/web-ui.service';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { IAsset } from '../models/iasset';
import { MatIcon,MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { interval, Observable, Subscription, switchMap } from 'rxjs';
import { IAssetPositionHistory } from '../models/IAssetPositionHistory';


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

  private updateSubscription!: Subscription;
  floorMaps? : IFloorMap[];
  floorMapMap = new Map<number, string>();
  selectedFloorMapId?: number;
  assets? : IAsset[];

  constructor(private webUiService: WebUiService){}
  
  ngOnInit(): void {
    this.fetchFloorMaps();

    this.updateSubscription = interval(2000) 
    .pipe(
      switchMap(() => {
        if (!this.selectedFloorMapId) return []; 
        return this.webUiService.getAssets(); 
      })
    )
    .subscribe((data: IAsset[] | undefined) => {
      if (data) {
        this.assets = data.filter(asset => asset.floorMapId === this.selectedFloorMapId);
      }
    });

  }

  getAssetPosition(asset: IAsset): { top: number, left: number } {

    const imageWidth = 1200; 
    const imageHeight = 800;  

    const gridColumns = 50;
    const gridRows = 50;

    const zoneWidth = imageWidth / gridColumns; 
    const zoneHeight = imageHeight / gridRows;  

    const left = asset.x * zoneWidth;  
    const top = asset.y * zoneHeight;  

    return { top, left };
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
