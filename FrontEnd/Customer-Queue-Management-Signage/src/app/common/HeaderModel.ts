export interface HeaderModel {
    branchCodeCbs: string;
    functionId: string;
    operationId: string;
    language: string;
}

export class HeaderModel implements HeaderModel {
    constructor(
        public branchCodeCbs: string,
        public functionId: string,
        public operationId: string,
        public language: string
    ) { }
}
