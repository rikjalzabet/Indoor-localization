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

  constructor(private http: HttpClient) { }

    public getAssets(): Observable<IAsset[]> {
      return this.http.get<IAsset[]>(`${this.apiUrl}/assets`);
    }

    public getFloorMaps(): Observable<IFloorMap[]> {
      return this.http.get<IFloorMap[]>(`${this.apiUrl}/floormaps`);
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

