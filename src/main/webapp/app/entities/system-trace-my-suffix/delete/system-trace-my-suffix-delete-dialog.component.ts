import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISystemTraceMySuffix } from '../system-trace-my-suffix.model';
import { SystemTraceMySuffixService } from '../service/system-trace-my-suffix.service';

@Component({
  templateUrl: './system-trace-my-suffix-delete-dialog.component.html',
})
export class SystemTraceMySuffixDeleteDialogComponent {
  systemTrace?: ISystemTraceMySuffix;

  constructor(protected systemTraceService: SystemTraceMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.systemTraceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
