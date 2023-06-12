import React, { ReactNode, useState } from 'react';
import PropTypes from 'prop-types';
import { alertService, AlertType } from '../services/alert.service';
import { PropsWithChildren } from 'react';
import { useEffect } from 'react';

type AlertProps = {
    id: string,
    fade: boolean,
    alertType: string;
    alertMessage: string
    children: ReactNode
};
const Alert: React.FC<AlertProps> = (props) => {
    return (
        <div>
            <p>{props.alertMessage}</p>
        </div>
    );
};

export default Alert;