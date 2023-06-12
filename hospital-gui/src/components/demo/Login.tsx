import React, { useState } from "react";
import { Resolver, SubmitHandler, useForm } from "react-hook-form";
import { Link, NavigateFunction, useNavigate } from 'react-router-dom';
import { login } from "../../services/auth.service";


interface IFormInputs {
    firstName: string
    lastName: string
}

const onSubmit: SubmitHandler<IFormInputs> = data => console.log(data);


const Login: React.FC = () => {
    const { register, formState: { errors }, handleSubmit } = useForm<IFormInputs>();

    return (
        <section className="section min-vh-100">
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">
                    {/*
                <div className="d-flex justify-content-center py-4">
                    <a href="index.html" className="logo d-flex align-items-center w-auto">
                        <img src="assets/img/logo.png" alt="" />
                            <span className="d-none d-lg-block">Gagans</span>
                    </a>
                </div>
    */}
                    <div className="card mb-3">
                        <div className="card-body">
                            <div className="pt-4 pb-2">
                                <h5 className="card-title text-center pb-0 fs-4">Login to Your Account</h5>
                                <p className="text-center small">Enter your username &amp; password to login</p>
                            </div>
                            <form className="row g-3 needs-validation" noValidate={true}>
                                <div className="col-12">
                                    <label htmlFor="yourUsername" className="form-label">Username</label>
                                    <div className="input-group has-validation">
                                        <span className="input-group-text" id="inputGroupPrepend">@</span>
                                        <input type="text" name="username" className="form-control" id="yourUsername" required />
                                        <div className="invalid-feedback">Please enter your username.</div>
                                    </div>
                                </div>
                                <div className="col-12">
                                    <label htmlFor="yourPassword" className="form-label">Password</label>
                                    <input type="password" name="password" className="form-control" id="yourPassword" required />
                                    <div className="invalid-feedback">Please enter your password!</div>
                                </div>
                                <div className="col-12">
                                    <div className="form-check">
                                        <input className="form-check-input" type="checkbox" name="remember" value="true" id="rememberMe" />
                                        <label className="form-check-label" htmlFor="rememberMe">Remember me</label>
                                    </div>
                                </div>
                                <div className="col-12">
                                    <button className="btn btn-primary w-100" type="submit">Login</button>
                                </div>
                                <div className="col-12">
                                    <p className="small mb-0">Don't have account?
                                        <Link to={"/register"} >Create an account</Link>
                                    </p>
                                </div>
                            </form>
                        </div>
                    </div>
                    {/*
                <div className="credits"> Designed by
                    <a href="https://bootstrapmade.com/">BootstrapMade</a>
                </div>
*/}
                </div>
            </form>
        </section>
    );
};

export default Login;