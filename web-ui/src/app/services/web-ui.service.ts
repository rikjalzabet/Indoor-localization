import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IAsset } from '../models/iasset';
import { IFloorMap } from '../models/IFloorMap';
import { IAssetPositionHistory } from '../models/IAssetPositionHistory';
import { HttpClient } from '@angular/common/http';
import { IAssetZoneHistory } from '../models/IAssetZoneHistory';
import { IZones } from '../models/IZones';


@Injectable({
  providedIn: 'root'
})
export class WebUiService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }
    public getAssets(): Observable<IAsset[]> {
      return this.http.get<IAsset[]>(`${this.apiUrl}/assets`);
    }

    public getFloorMaps(): Observable<IFloorMap[]> {
      return this.http.get<IFloorMap[]>(`${this.apiUrl}/floormaps`);
    }

    public getZones(): Observable<IZones[]> {
      const mockFloorMaps: IZones[] = [
        {
          id :1,
          name: 'Zone A',
          points: '1'
        },
        {
          id :3,
          name: 'Zone B',
          points: '1'
        },
        {
          id :3,
          name: 'Zone C',
          points: '1'
        }
      ];

      return of(mockFloorMaps);
    }

    public deleteAsset(Id: number): void {
      this.http.delete(`${this.apiUrl}/assets/${Id}`).subscribe({
        next: () => {
          console.log(`Asset with id ${Id} deleted successfully.`);
          this.getAssets();
        },
        error: (err) => {
          console.error('Error deleting asset:', err);
        }
      });    
    }

    public addAsset(Asset: IAsset): void{
      this.http.post(`${this.apiUrl}/assets`, Asset)
      .subscribe({
        next: (response) => {
          console.log('Asset added successfully', response);
          this.getAssets();
        },
        error: (err) => {
          console.error('Error adding asset:', err);
        }
      });
    }

    public updateAsset(Asset: IAsset): void{
      this.http.put(`${this.apiUrl}/assets/${Asset.id}`, Asset).subscribe({
        next: () => {
          console.log(`Asset with id ${Asset.id} updated successfully.`);
          this.getAssets();
        },
        error: (err) => {
          console.error('Error updating asset:', err);
        }
      });    
    }

    public getAssetPositionHistory(FloorMapId: number): Observable<IAssetPositionHistory[]>  {
      return this.http.get<IAssetPositionHistory[]>(`${this.apiUrl}/assetPositionHistory/${FloorMapId}`);
      if(FloorMapId==1){
      const mockAssetPositionHistory: IAssetPositionHistory[] = [
        {
          id: 1,
          assetId: 1,
          x: 100,
          y: 100,
          dateTime: new Date('2024-12-09T10:00:00Z'),
          floorMapId: 1,
      },
      {
          id: 2,
          assetId: 1,
          x: 200,
          y: 150,
          dateTime: new Date('2024-12-09T10:05:00Z'),
          floorMapId: 1,
      },
      {
          id: 3,
          assetId: 2,
          x: 50.34,
          y: 90.78,
          dateTime: new Date('2024-12-09T10:10:00Z'),
          floorMapId: 1,
      }
      ];

      return of(mockAssetPositionHistory);
    }else{
      const mockAssetPositionHistory: IAssetPositionHistory[] = [
        {
          id: 6,
          assetId: 2,
          x: 100,
          y: 100,
          dateTime: new Date('2024-12-09T10:00:00Z'),
          floorMapId: 1,
      },
      ];

      return of(mockAssetPositionHistory);
    }}

    public getAssetZoneHistory(): Observable<IAssetZoneHistory[]>  {
      const mockAssetPositionHistory: IAssetZoneHistory[] = [
        {
          id: 1,
          assetId: 1,
          zoneId : 1,
        enterDateTime :new Date('2024-12-09T10:00:00Z'),
          exitDateTime: new Date('2024-12-09T10:00:00Z'),
          retentionTime: 1,
      },
      {
        id: 2,
        assetId: 1,
        zoneId : 1,
      enterDateTime :new Date('2024-12-09T10:00:00Z'),
        exitDateTime: new Date('2024-12-09T10:00:00Z'),
        retentionTime: 1,
      },
      ];

      return of(mockAssetPositionHistory);
    }
  }

