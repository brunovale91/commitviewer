import React from 'react';
import { Menu } from './Menu';
import { Header } from './Header';
import './Frame.css';

export class Frame extends React.Component {
    render() {
        return (
        <div>
            <Header></Header>
            <div className="frame">
                <Menu></Menu>
                <div className="page">
                    {this.props.children}
                </div>
            </div>
        </div>
        );
    }
}