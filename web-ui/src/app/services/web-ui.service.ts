import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IAsset } from '../models/iasset';
import { IFloorMap } from '../models/IFloorMap';
import { HttpClient } from '@angular/common/http';

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
  }

