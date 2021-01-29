import { HeaderModel } from './HeaderModel';
export interface FinRequestModel {
    headerModel: HeaderModel;
    finData: any;
}
export class FinRequest implements FinRequestModel {
    constructor(
        public headerModel: HeaderModel,
        public finData: any) { }
}
