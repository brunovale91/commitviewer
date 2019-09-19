import React from 'react';
import './Header.css';

export class Header extends React.Component {
    render() {
        return (
        <div className="header">
            <div className="logo">
                <img src="https://app.codacy.com/legacy/versioned/images/codacy-white.svg" alt="Codacy, the automated code review software"></img>
            </div>
            <div className="header-text">Codacy Exercise</div>
        </div>
        );
    }
}