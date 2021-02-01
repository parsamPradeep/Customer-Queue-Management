export interface RequestModel {
    data: any;
    applicationDate: any;
    branchCode: string;
}
export class FinRequest implements RequestModel {
    constructor(public applicationDate: any,
        public branchCode: string,
        public data: any) { }
}
