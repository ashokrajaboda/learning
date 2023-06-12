import { ChangeEventHandler } from "react";

export interface IErrors {
    errorList?: Array<Error>;
}

export interface IError {
    errorType: string;
    errorMessage: string;
}

export default interface IResponseDTO<T> {
    success: boolean;
    httpStatus: string;
    data?: T;
    errors?: IErrors | null;
}

export interface IHeaderProps {
    appName: string;
    children?: React.ReactNode | null;
    toggleSidebar?: ChangeEventHandler | undefined;
};

export enum MenuType {
    Menu,
    Heading
}

export interface IMenuItem {
    id: number,
    key: string;
    type: MenuType,
    label: string,
    active: boolean;
    iconStyle?: string;
    path?: string;
    elements?: React.ReactNode | null;
    submenus?: Array<IMenuItem> | null;
}

export interface IMenuItems extends Array<IMenuItem> {

}