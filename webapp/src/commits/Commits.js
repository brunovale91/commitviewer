import React from 'react';
import './Commits.css';

export class Commits extends React.Component {

    constructor() {
        super();
        this.state = {
            owner: "",
            repo: "",
            commits: [],
            page: 0
        }
    }

    async fetchCommits(page) {
        let reponse = await fetch('/' + this.state.owner + "/" + this.state.repo + "/commits?page=" + page);
        if (reponse.status === 200) {
            return await reponse.json()
        }
        return [];
    }

    async getCommits() {
        if (this.state.owner !== "" && this.state.repo !== "") {
            const commits = await this.fetchCommits(1);
            this.setState({
                commits: commits,
                page: 1
            })
        }

    }
    
    handleScroll = async (e) => {
        const bottom = e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight;
        if (bottom) {
            let commits = await this.fetchCommits(this.state.page + 1);
            commits = this.state.commits.concat(commits);
            this.setState({
                commits: commits,
                page: this.state.page + 1
            });
         }
    }

    render() {
        let commitsTrs = [];
        for(let commit of this.state.commits) {
            if(commit) {
                commitsTrs.push(
                    <tr key={commit.id}>
                        <td>{commit.id}</td>
                        <td>{commit.authorEmail}</td>
                        <td>{commit.message}</td>
                        <td>{new Date(commit.time * 1000).toISOString()}</td>
                    </tr>);
            }
        }
        return (
        <div className="repo-inputs">
            <label>
                Owner:
                <input type="text" value={this.state.owner} onChange={(event) => {
                    this.setState({
                        owner: event.target.value
                    });
                }} />
            </label>
            <label>
                Repo:
                <input type="text" value={this.state.repo} onChange={(event) => {
                    this.setState({
                        repo: event.target.value
                    });
                }} />
            </label>
            <button onClick={this.getCommits.bind(this)}>Get Commits</button>
            <table className="table">
                <thead>
                    <tr>
                        <th>Commit Id</th>
                        <th>Author Email</th>
                        <th>Message</th>
                        <th>Time</th>
                    </tr>
                </thead>
            </table>
            <table className="table">
                <tbody  onScroll={this.handleScroll}>
                    {commitsTrs}
                </tbody>
            </table>
        </div>
        );
    }
}