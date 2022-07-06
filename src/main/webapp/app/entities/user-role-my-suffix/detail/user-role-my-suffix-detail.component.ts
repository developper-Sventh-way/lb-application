import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserRoleMySuffix } from '../user-role-my-suffix.model';

@Component({
  selector: 'jhi-user-role-my-suffix-detail',
  templateUrl: './user-role-my-suffix-detail.component.html',
})
export class UserRoleMySuffixDetailComponent implements OnInit {
  userRole: IUserRoleMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRole }) => {
      this.userRole = userRole;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
