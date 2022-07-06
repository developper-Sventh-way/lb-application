import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'borlette-conf-my-suffix',
        data: { pageTitle: 'lbApp.borletteConf.home.title' },
        loadChildren: () => import('./borlette-conf-my-suffix/borlette-conf-my-suffix.module').then(m => m.BorletteConfMySuffixModule),
      },
      {
        path: 'coupon-gratuit-conf-my-suffix',
        data: { pageTitle: 'lbApp.couponGratuitConf.home.title' },
        loadChildren: () =>
          import('./coupon-gratuit-conf-my-suffix/coupon-gratuit-conf-my-suffix.module').then(m => m.CouponGratuitConfMySuffixModule),
      },
      {
        path: 'credit-solde-my-suffix',
        data: { pageTitle: 'lbApp.creditSolde.home.title' },
        loadChildren: () => import('./credit-solde-my-suffix/credit-solde-my-suffix.module').then(m => m.CreditSoldeMySuffixModule),
      },
      {
        path: 'limit-conf-borlette-my-suffix',
        data: { pageTitle: 'lbApp.limitConfBorlette.home.title' },
        loadChildren: () =>
          import('./limit-conf-borlette-my-suffix/limit-conf-borlette-my-suffix.module').then(m => m.LimitConfBorletteMySuffixModule),
      },
      {
        path: 'limit-conf-manager-my-suffix',
        data: { pageTitle: 'lbApp.limitConfManager.home.title' },
        loadChildren: () =>
          import('./limit-conf-manager-my-suffix/limit-conf-manager-my-suffix.module').then(m => m.LimitConfManagerMySuffixModule),
      },
      {
        path: 'limit-conf-point-my-suffix',
        data: { pageTitle: 'lbApp.limitConfPoint.home.title' },
        loadChildren: () =>
          import('./limit-conf-point-my-suffix/limit-conf-point-my-suffix.module').then(m => m.LimitConfPointMySuffixModule),
      },
      {
        path: 'paiement-banque-my-suffix',
        data: { pageTitle: 'lbApp.paiementBanque.home.title' },
        loadChildren: () =>
          import('./paiement-banque-my-suffix/paiement-banque-my-suffix.module').then(m => m.PaiementBanqueMySuffixModule),
      },
      {
        path: 'point-of-sale-my-suffix',
        data: { pageTitle: 'lbApp.pointOfSale.home.title' },
        loadChildren: () => import('./point-of-sale-my-suffix/point-of-sale-my-suffix.module').then(m => m.PointOfSaleMySuffixModule),
      },
      {
        path: 'point-of-sale-conf-my-suffix',
        data: { pageTitle: 'lbApp.pointOfSaleConf.home.title' },
        loadChildren: () =>
          import('./point-of-sale-conf-my-suffix/point-of-sale-conf-my-suffix.module').then(m => m.PointOfSaleConfMySuffixModule),
      },
      {
        path: 'point-of-sale-membership-my-suffix',
        data: { pageTitle: 'lbApp.pointOfSaleMembership.home.title' },
        loadChildren: () =>
          import('./point-of-sale-membership-my-suffix/point-of-sale-membership-my-suffix.module').then(
            m => m.PointOfSaleMembershipMySuffixModule
          ),
      },
      {
        path: 'membership-conf-my-suffix',
        data: { pageTitle: 'lbApp.membershipConf.home.title' },
        loadChildren: () =>
          import('./membership-conf-my-suffix/membership-conf-my-suffix.module').then(m => m.MembershipConfMySuffixModule),
      },
      {
        path: 'pos-configuration-my-suffix',
        data: { pageTitle: 'lbApp.pOSConfiguration.home.title' },
        loadChildren: () =>
          import('./pos-configuration-my-suffix/pos-configuration-my-suffix.module').then(m => m.POSConfigurationMySuffixModule),
      },
      {
        path: 'system-trace-my-suffix',
        data: { pageTitle: 'lbApp.systemTrace.home.title' },
        loadChildren: () => import('./system-trace-my-suffix/system-trace-my-suffix.module').then(m => m.SystemTraceMySuffixModule),
      },
      {
        path: 'ticket-my-suffix',
        data: { pageTitle: 'lbApp.ticket.home.title' },
        loadChildren: () => import('./ticket-my-suffix/ticket-my-suffix.module').then(m => m.TicketMySuffixModule),
      },
      {
        path: 'ticket-option-my-suffix',
        data: { pageTitle: 'lbApp.ticketOption.home.title' },
        loadChildren: () => import('./ticket-option-my-suffix/ticket-option-my-suffix.module').then(m => m.TicketOptionMySuffixModule),
      },
      {
        path: 'tirage-my-suffix',
        data: { pageTitle: 'lbApp.tirage.home.title' },
        loadChildren: () => import('./tirage-my-suffix/tirage-my-suffix.module').then(m => m.TirageMySuffixModule),
      },
      {
        path: 'transaction-histories-my-suffix',
        data: { pageTitle: 'lbApp.transactionHistories.home.title' },
        loadChildren: () =>
          import('./transaction-histories-my-suffix/transaction-histories-my-suffix.module').then(
            m => m.TransactionHistoriesMySuffixModule
          ),
      },
      {
        path: 'user-payment-my-suffix',
        data: { pageTitle: 'lbApp.userPayment.home.title' },
        loadChildren: () => import('./user-payment-my-suffix/user-payment-my-suffix.module').then(m => m.UserPaymentMySuffixModule),
      },
      {
        path: 'user-payment-conf-my-suffix',
        data: { pageTitle: 'lbApp.userPaymentConf.home.title' },
        loadChildren: () =>
          import('./user-payment-conf-my-suffix/user-payment-conf-my-suffix.module').then(m => m.UserPaymentConfMySuffixModule),
      },
      {
        path: 'user-role-my-suffix',
        data: { pageTitle: 'lbApp.userRole.home.title' },
        loadChildren: () => import('./user-role-my-suffix/user-role-my-suffix.module').then(m => m.UserRoleMySuffixModule),
      },
      {
        path: 'user-sale-account-my-suffix',
        data: { pageTitle: 'lbApp.userSaleAccount.home.title' },
        loadChildren: () =>
          import('./user-sale-account-my-suffix/user-sale-account-my-suffix.module').then(m => m.UserSaleAccountMySuffixModule),
      },
      {
        path: 'utilisateur-my-suffix',
        data: { pageTitle: 'lbApp.utilisateur.home.title' },
        loadChildren: () => import('./utilisateur-my-suffix/utilisateur-my-suffix.module').then(m => m.UtilisateurMySuffixModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
