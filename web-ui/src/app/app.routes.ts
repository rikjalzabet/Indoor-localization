import { RouterModule, Routes } from '@angular/router';
import { AssetManagementComponent } from './asset-management/asset-management.component';
import { ReportsComponent } from './reports/reports.component';
import { NgModule } from '@angular/core';

export const routes: Routes = [
    { path: '', redirectTo: 'asset-managment', pathMatch: 'full'},
    { path: 'asset-managment', component: AssetManagementComponent },
    { path: 'reports', component: ReportsComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule {}