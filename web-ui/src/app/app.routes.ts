import { RouterModule, Routes } from '@angular/router';
import { AssetManagementComponent } from './asset-management/asset-management.component';
import { ReportsComponent } from './reports/reports.component';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
    { path: '', redirectTo: 'dashboard', pathMatch: 'full'},
    { path: 'asset-managment', component: AssetManagementComponent },
    { path: 'reports', component: ReportsComponent },
    { path: 'dashboard', component: DashboardComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule {}