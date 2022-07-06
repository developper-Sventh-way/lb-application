import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMembershipConfMySuffix } from '../membership-conf-my-suffix.model';

@Component({
  selector: 'jhi-membership-conf-my-suffix-detail',
  templateUrl: './membership-conf-my-suffix-detail.component.html',
})
export class MembershipConfMySuffixDetailComponent implements OnInit {
  membershipConf: IMembershipConfMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membershipConf }) => {
      this.membershipConf = membershipConf;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
