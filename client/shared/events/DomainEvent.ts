abstract class DomainEvent {
    public readonly eventId: string;
    public readonly occurredOn: Date;
    public readonly eventVersion: number;

    constructor() {
        this.eventId = this.generateEventId();
        this.occurredOn = new Date();
        this.eventVersion = 1;
    }

    private generateEventId(): string {
        return `${Date.now()}-${Math.random().toString(36).slice(2, 9)}`;
    }

    abstract getEventName(): string;

    abstract getAggregateId(): string;

    public toString(): string {
        return `${this.getEventName()} [${this.eventId}] occurred on ${this.occurredOn.toISOString()}`;
    }

    public toPrimitives(): Object {
        return {
            eventId: this.eventId,
            eventName: this.getEventName(),
            aggregateId: this.getAggregateId(),
            occurredOn: this.occurredOn.toISOString(),
            eventVersion: this.eventVersion
        }
    }

}

export default DomainEvent;