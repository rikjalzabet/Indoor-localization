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
import { ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IZone } from '../models/IZone';


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
  zones? : IZone[];

  constructor(
    private webUiService: WebUiService,
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute, 
    private router: Router 
  ){}
  
  ngOnInit(): void {

    this.route.paramMap.subscribe((params) => {
      const floorMapId = params.get('floorMapId');
      if (floorMapId) {
        this.selectedFloorMapId = +floorMapId;
        this.getAssets(this.selectedFloorMapId);
      }
    });

    this.fetchFloorMaps();

    this.fetchZones();

    this.updateSubscription = interval(1000)
    .pipe(
      switchMap(() => this.webUiService.getAssets())
    )
    .subscribe((data: IAsset[] | undefined) => {
      if (data) {
        this.assets = [];
        this.assets = [...data.filter(
          (asset) => asset.floorMapId === this.selectedFloorMapId
        )];
        console.log('Ažurirani assets:', this.assets[0]);
        this.cdr.detectChanges(); 
      }
    });

  }

  ngOnDestroy(): void {
    if (this.updateSubscription) {
      this.updateSubscription.unsubscribe();
    }
  }
  
  getAssetPosition(asset: IAsset): { top: number, left: number } {
    const imageWidth = 780;
    const imageHeight = 610; 
  
    const gridColumns = 100; 
    const gridRows = 100; 
  
    const marginTop = 50;
    const marginBottom = 50;
    const marginLeft = 50;
    const marginRight = 50; 
  
    const adjustedWidth = imageWidth - marginLeft - marginRight;
    const adjustedHeight = imageHeight - marginTop - marginBottom;
  
    const left = marginLeft + (asset.x / gridColumns) * adjustedWidth;
    const top = marginTop + (asset.y / gridRows) * adjustedHeight;
  
    return { top, left };
  }  

  scaleZonePoints(points: {x: number, y: number }[]): { x: number, y: number }[] {
    
    var imageWidth = 780;
    var imageHeight = 610; 

    
    if(this.selectedFloorMapId == 2){
      imageWidth = 805;
      imageHeight = 590; 
    }


    const gridColumns = 100; 
    const gridRows = 100; 
  
    const marginTop = 50;
    const marginBottom = 50;
    const marginLeft = 50;
    const marginRight = 50; 

    const adjustedWidth = imageWidth - marginLeft - marginRight;
    const adjustedHeight = imageHeight - marginTop - marginBottom;

    const scalled = points.map(point => ({
      x: marginLeft + (Number(point.x) / gridColumns) * adjustedWidth,
      y: marginTop + (Number(point.y) / gridRows) * adjustedHeight,
    }));
    
    return scalled;
  }
  
  formatZonePoints(points: { x: number, y: number }[]): string {
    if (!points || points.length === 0) {
      console.error('Invalid or empty points array:', points);
      return '';
    }
    const formattedPoints = points.map(point => `${point.x},${point.y}`).join(' ');
    return formattedPoints;
  }

  getZoneColor(zoneId: number): string {
    const colors = ['rgba(255, 0, 0, 0.3)', 'rgba(0, 255, 0, 0.3)', 'rgba(0, 0, 255, 0.3)', 'rgba(255, 255, 0, 0.3)'];
    return colors[zoneId % colors.length];
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

  fetchZones(): void {
    this.webUiService.getZones().subscribe({
      next: (data) => {
        this.zones = data;
        console.log('Dohvaćene zone:', this.zones);
      },
      error: (err) => console.error('Error fetching data', err)
    });
  }

  getFloorMapImage(floorMapId?: number): string | undefined {
    if (floorMapId === undefined) {
      return undefined;
    }
    const floorMap = this.floorMaps?.find((fm) => fm.id === floorMapId);
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
    this.router.navigate(['/dashboard', floorMapId]);
  }

  trackByAssetId(index: number, asset: IAsset): number {
    return asset.id; 
  }
}
