/*
 * ***** BEGIN LICENSE BLOCK *****
 * Maldua Zimbra ActiveSync Auth Extension
 * Copyright (C) 2025 BTACTIC, S.C.C.L.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * ***** END LICENSE BLOCK *****
 */

if(ZaSettings && ZaSettings.EnabledZimlet["com_btactic_activesyncauth_admin"]){

    function com_btactic_activesyncauth_ext () {

    }

    if (window.console && console.log) {
        console.log("Start loading com_btactic_activesyncauth_admin.js");
    }

    com_btactic_activesyncauth_ext.getFirstSwitchPosition = function (xFormObject) {
        var cnt = xFormObject.items.length;
        var i = 0;
        for(i = 0; i <cnt; i++) {
            if(xFormObject.items[i].type=="switch")
                break;
        }

        return i;
    }

    // Using getResource from a ZmZimletBase object does not seem to work in admin
    com_btactic_activesyncauth_admin.zimletImagesPath = "/service/zimlet/com_btactic_activesyncauth_admin/images"


    com_btactic_activesyncauth_admin.malduaHeader =
      '<a target="_blank" href="https://github.com/maldua-suite/maldua-suite">' +
      '<img align="right" alt="Maldua Suite for Zimbra Collaboration Server" src="' +
      com_btactic_activesyncauth_admin.zimletImagesPath + "/" + "maldua_logo.png" +
      '">' +
      '</a>'

    com_btactic_activesyncauth_admin.zetaPromoWithImage =
      '<img src="' +
      com_btactic_activesyncauth_admin.zimletImagesPath + "/" + "btactic_logo.png" +
      '">' +
      " " +
      com_btactic_activesyncauth_admin.zetaPromo +
      com_btactic_activesyncauth_admin.malduaHeader;

    com_btactic_activesyncauth_admin.zetaPromoCss = "font-size:16pt; font-weight: bold;";

    // Additional ActiveSync Auth attributes - Accounts (Definition)
    if (window.ZaAccount && ZaAccount.myXModel && ZaAccount.myXModel.items) {
        ZaAccount.myXModel.items.push({id: "zimbraFeatureMobileSyncEnabled", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureMobileSyncEnabled", choices: ZaModel.BOOLEAN_CHOICES});
    }

    // Additional ActiveSync Auth attributes - Accounts (Edit)
    if(ZaTabView.XFormModifiers["ZaAccountXFormView"]) {
        com_btactic_activesyncauth_ext.AccountXFormModifier= function (xFormObject,entry) {

            var firstSwitchPosition = com_btactic_activesyncauth_ext.getFirstSwitchPosition(xFormObject);
            var activesyncauthTabIx = ++this.TAB_INDEX;

            var tabBar = xFormObject.items[1];
            tabBar.choices.push({value:activesyncauthTabIx, label:com_btactic_activesyncauth_admin.zetaActiveSyncAuthTab});

            var activesyncauthAccountTab={
                type:_ZATABCASE_,
                numCols:1,
                caseKey:activesyncauthTabIx,
                items: [
                    {label: null, type: _OUTPUT_, value: com_btactic_activesyncauth_admin.zetaPromoWithImage, colSpan:"*", cssStyle:com_btactic_activesyncauth_admin.zetaPromoCss},
                    {type:_SPACER_, colSpan:"*"},
                    {type:_ZA_TOP_GROUPER_,
                        label:com_btactic_activesyncauth_admin.zetaActiveSyncAuthTab,
                        items:[
                            {ref: "zimbraFeatureMobileSyncEnabled", type: _SUPER_CHECKBOX_, checkBoxLabel: com_btactic_activesyncauth_admin.zimbraFeatureMobileSyncEnabled, msgName: com_btactic_activesyncauth_admin.zimbraFeatureMobileSyncEnabled, trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel: ZaMsg.NAD_ResetToCOS}
                        ]
                    }
                ]
            };

            xFormObject.items[firstSwitchPosition].items.push(activesyncauthAccountTab);
        }
        ZaTabView.XFormModifiers["ZaAccountXFormView"].push(com_btactic_activesyncauth_ext.AccountXFormModifier);
    }

    // Additional ActiveSync Auth attributes - ClassOfService (Definition)
    if (window.ZaCos && ZaCos.myXModel && ZaCos.myXModel.items) {
        ZaCos.myXModel.items.push({id: "zimbraFeatureMobileSyncEnabled", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureMobileSyncEnabled", choices: ZaModel.BOOLEAN_CHOICES});
    }

    // Additional ActiveSync Auth attributes - ClassOfService (Edit)
    if(ZaTabView.XFormModifiers["ZaCosXFormView"]) {
        com_btactic_activesyncauth_ext.myCosXFormModifier= function (xFormObject,entry) {

            var firstSwitchPosition = com_btactic_activesyncauth_ext.getFirstSwitchPosition(xFormObject);
            var activesyncauthTabIx = ++this.TAB_INDEX;

            var tabBar = xFormObject.items[1];
            tabBar.choices.push({value:activesyncauthTabIx, label:com_btactic_activesyncauth_admin.zetaActiveSyncAuthTab});

            var activesyncauthAccountTab={
                type:_ZATABCASE_,
                numCols:1,
                caseKey:activesyncauthTabIx,
                items: [
                    {label: null, type: _OUTPUT_, value: com_btactic_activesyncauth_admin.zetaPromoWithImage, colSpan:"*", cssStyle:com_btactic_activesyncauth_admin.zetaPromoCss},
                    {type:_SPACER_, colSpan:"*"},
                    {type:_ZA_TOP_GROUPER_,
                        label:com_btactic_activesyncauth_admin.zetaActiveSyncAuthTab,
                        items:[
                            {ref: "zimbraFeatureMobileSyncEnabled", type: _CHECKBOX_, label: com_btactic_activesyncauth_admin.zimbraFeatureMobileSyncEnabled, msgName: com_btactic_activesyncauth_admin.zimbraFeatureMobileSyncEnabled, trueValue: "TRUE", falseValue: "FALSE", labelLocation: _LEFT_}
                        ]
                    }
                ]
            };

            xFormObject.items[firstSwitchPosition].items.push(activesyncauthAccountTab);
        }
        ZaTabView.XFormModifiers["ZaCosXFormView"].push(com_btactic_activesyncauth_ext.myCosXFormModifier);
    }

    // Additional ActiveSync Auth attributes - Accounts (New)
    com_btactic_activesyncauth_ext.ACC_WIZ_GROUP = {
        type:_ZAWIZGROUP_,
        items:[
            {label: null, type: _OUTPUT_, value: com_btactic_activesyncauth_admin.zetaPromoWithImage, colSpan:"*", cssStyle:com_btactic_activesyncauth_admin.zetaPromoCss},
            {type:_SPACER_, colSpan:"*"},
            {
              type: _ZAWIZ_TOP_GROUPER_,
              label:com_btactic_activesyncauth_admin.zetaActiveSyncAuthTab,
              colSizes : [ "200px", "400px" ],
              numCols : 2,
              items : [
                {ref: "zimbraFeatureMobileSyncEnabled", type: _SUPER_WIZ_CHECKBOX_, checkBoxLabel: com_btactic_activesyncauth_admin.zimbraFeatureMobileSyncEnabled, msgName: com_btactic_activesyncauth_admin.zimbraFeatureMobileSyncEnabled, trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel: ZaMsg.NAD_ResetToCOS}
              ]
            }
        ]
    };

    if(ZaXDialog.XFormModifiers["ZaNewAccountXWizard"]) {
        com_btactic_activesyncauth_ext.AccountXWizModifier= function (xFormObject, entry) {

            var firstSwitchPosition = com_btactic_activesyncauth_ext.getFirstSwitchPosition(xFormObject);
            var activesyncauthTabIx = ++this.TAB_INDEX;

            this.stepChoices.push({value:activesyncauthTabIx, label:com_btactic_activesyncauth_admin.zetaActiveSyncAuthTab});
            this._lastStep = this.stepChoices.length;

            var activesyncauthStep={type:_CASE_, numCols:1, caseKey:activesyncauthTabIx, tabGroupKey:activesyncauthTabIx,
                items: [com_btactic_activesyncauth_ext.ACC_WIZ_GROUP]
            };

            xFormObject.items[firstSwitchPosition].items.push(activesyncauthStep);

        }
        ZaXDialog.XFormModifiers["ZaNewAccountXWizard"].push(com_btactic_activesyncauth_ext.AccountXWizModifier);
    }

    // Additional ActiveSync Auth attributes - ClassOfService (New)
    com_btactic_activesyncauth_ext.COS_WIZ_GROUP = {
        type:_ZAWIZGROUP_,
        items:[
            {label: null, type: _OUTPUT_, value: com_btactic_activesyncauth_admin.zetaPromoWithImage, colSpan:"*", cssStyle:com_btactic_activesyncauth_admin.zetaPromoCss},
            {type:_SPACER_, colSpan:"*"},
            {
              type: _ZAWIZ_TOP_GROUPER_,
              label:com_btactic_activesyncauth_admin.zetaActiveSyncAuthTab,
              colSizes : [ "200px", "400px" ],
              numCols : 2,
              items : [
                {ref: "zimbraFeatureMobileSyncEnabled", type: _WIZ_CHECKBOX_, label: com_btactic_activesyncauth_admin.zimbraFeatureMobileSyncEnabled, msgName: com_btactic_activesyncauth_admin.zimbraFeatureMobileSyncEnabled, trueValue: "TRUE", falseValue: "FALSE"}
              ]
            }
        ]
    };

    if(ZaXDialog.XFormModifiers["ZaNewCosXWizard"]) {
        com_btactic_activesyncauth_ext.CosXWizModifier= function (xFormObject, entry) {

            var firstSwitchPosition = com_btactic_activesyncauth_ext.getFirstSwitchPosition(xFormObject);
            var activesyncauthTabIx = ++this.TAB_INDEX;

            this.stepChoices.push({value:activesyncauthTabIx, label:com_btactic_activesyncauth_admin.zetaActiveSyncAuthTab});
            this._lastStep = this.stepChoices.length;

            var activesyncauthStep={type:_CASE_, numCols:1, caseKey:activesyncauthTabIx, tabGroupKey:activesyncauthTabIx,
                items: [com_btactic_activesyncauth_ext.COS_WIZ_GROUP]
            };

            xFormObject.items[firstSwitchPosition].items.push(activesyncauthStep);

        }
        ZaXDialog.XFormModifiers["ZaNewCosXWizard"].push(com_btactic_activesyncauth_ext.CosXWizModifier);
    }

}
