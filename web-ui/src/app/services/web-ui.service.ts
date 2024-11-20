import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IAsset } from '../models/iasset';

@Injectable({
  providedIn: 'root'
})
export class WebUiService {

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

  }

