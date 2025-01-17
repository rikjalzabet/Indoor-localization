import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IAsset } from '../models/iasset';
import { IFloorMap } from '../models/IFloorMap';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class WebUiService {

  private apiUrl = 'https://localhost:8000';

  constructor() { }

    public getAssets(): Observable<IAsset[]> {
      const mockAssets: IAsset[] = [
        {
          id: 1,
          name: 'Asset A',
          x: 12.34,
          y: 56.78,
          lastSync: new Date('2024-11-01T10:00:00Z'),
          floorMapId: 1,
          active: true,
        },
        {
          id: 2,
          name: 'Asset B',
          x: 34.56,
          y: 78.90,
          lastSync: new Date('2024-11-10T15:30:00Z'),
          floorMapId: 1,
          active: false,
        },
        {
          id: 3,
          name: 'Asset C',
          x: 45.67,
          y: 89.01,
          lastSync: new Date('2024-11-11T12:45:00Z'),
          floorMapId: 2,
          active: true,
        },
        {
          id: 4,
          name: 'Asset D',
          x: 23.45,
          y: 67.89,
          lastSync: new Date('2024-11-12T08:20:00Z'),
          floorMapId: 2,
          active: true,
        },
        {
          id: 5,
          name: 'Asset E',
          x: 78.90,
          y: 12.34,
          lastSync: new Date('2024-11-12T09:00:00Z'),
          floorMapId: 3,
          active: false,
        },
      ];
  
      return of(mockAssets);
    }

    public getFloorMaps(): Observable<IFloorMap[]> {
      const mockFloorMaps: IFloorMap[] = [
        {
          id :1,
          name: 'Floor map A',
          image: 'Image 1'
        },
        {
          id :2,
          name: 'Floor map B',
          image: 'Image 2'
        },
        {
          id :3,
          name: 'Floor map C',
          image: 'Image 3'
        }
      ];

      return of(mockFloorMaps);
    }

    public deleteAsset(Id: number): void {
          console.log('Asset is deleted: ', Id)
    }

    public addAsset(Asset: IAsset): void{
      console.log('Asset is added',Asset);
    }

    public updateAsset(Asset: IAsset): void{
      console.log('Asset is updated',Asset);
    }
  }

