import React from "react";
import "./About.css";

export default function About() {
    return (
        <div className="about-container">
            <header className="about-header">
                <h1>About VaultDrop</h1>
            </header>

            <section className="about-content">
                <p>
                    <strong>VaultDrop</strong> is a secure and innovative platform designed
                    to simplify file storage, sharing, and management. Built with modern
                    technologies like Spring Boot and JWT authentication, VaultDrop ensures
                    your data remains safe and accessible.
                </p>

                <p>
                    VaultDrop is a proud <strong>subsidiary of SportsRoundups</strong>, a
                    multinational company, and is wholly owned by{" "}
                    <strong>NBTECH</strong>. As one of the aegis initiatives
                    under NBTECH, VaultDrop represents a commitment to excellence,
                    security, and innovation in digital solutions.
                </p>

                <p>
                    Our mission is to empower individuals and organizations with reliable
                    tools to protect and manage their digital assets, while maintaining the
                    highest standards of trust and transparency.
                </p>
            </section>

            <section className="about-disclaimer">
                <h2>Legal Disclaimer</h2>
                <p>
                    VaultDrop is provided as a secure file management platform under the
                    aegis of NBTECH Private Trust. While every effort is made to ensure
                    data integrity and security, VaultDrop and its parent organizations
                    (SportsRoundups Ltd. and NBTECH Private Trust) shall not be held liable
                    for any direct, indirect, or consequential damages arising from the use
                    of this platform. Users are solely responsible for maintaining the
                    confidentiality of their credentials and for compliance with applicable
                    laws and regulations.
                    contactUs@ sportsroundups1@gmail.com
                </p>
            </section>

            <footer className="about-footer">
                <p>© {new Date().getFullYear()} VaultDrop. All rights reserved.</p>
            </footer>
        </div>
    );
}
